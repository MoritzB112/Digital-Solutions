package es.uma.proyecto.war;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.ejb.GestionCuentas;
import es.uma.proyecto.ejb.GestionTransacciones;
import es.uma.proyecto.ejb.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.CuentaNoSuporteadaException;
import es.uma.proyecto.ejb.Excepciones.CuentasNoIgualesException;
import es.uma.proyecto.ejb.Excepciones.DepositoNoExisteException;
import es.uma.proyecto.ejb.Excepciones.SaldoInsuficianteException;
import es.uma.proyecto.ejb.Excepciones.TransaccionYaExisteException;
import es.uma.proyecto.jpa.Autorizacion;
import es.uma.proyecto.jpa.Cliente;
import es.uma.proyecto.jpa.Cuenta_Fintech;
import es.uma.proyecto.jpa.Cuenta_Referencia;
import es.uma.proyecto.jpa.Depositado_en;
import es.uma.proyecto.jpa.Depositado_en_PK;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Pooled_Account;
import es.uma.proyecto.jpa.Segregada;
import es.uma.proyecto.jpa.Transaccion;

@Named(value = "infoCuentas")
@RequestScoped
public class InfoCuentas implements Serializable {
	
	  private static final DecimalFormat df = new DecimalFormat("0.00");

	@Inject
    private InfoSesion sesion;
	
	@EJB
	private GestionTransacciones transacciones;
	
	@EJB
	private GestionCuentas gc;
	
	private Transaccion tr;
	private String depOr;
	private String depDest;
	
	public InfoCuentas() {
		tr=new Transaccion();
		depOr=new String();
		depDest=new String();
	}
	
	public String getDepOr() {
		return depOr;
	}

	public void setDepOr(String depOr) {
		this.depOr = depOr;
	}
	
	public String getDepDest() {
		return depDest;
	}

	public void setDepDest(String depDest) {
		this.depDest = depDest;
	}

	public Transaccion getTr() {
		return tr;
	}

	public void setTr(Transaccion tr) {
		this.tr = tr;
	}
	
	public String elegirEmpresa(Empresa em) {
		
		sesion.setEm(em);
		sesion.refreshAll();
		
		return "clientView.xhtml";
	}
	
	public String tipoCuenta(Cuenta_Fintech cf) {
		if(esSegregada(cf)) {
			return "Segregada";
		}else if(esPooled(cf)) {
			return "Pooled_Account";
		}
		return null;
	}
	
	public boolean esSegregada(Cuenta_Fintech cf) {
		return cf instanceof Segregada;
	}
	
	public boolean esPooled(Cuenta_Fintech cf) {
		return cf instanceof Pooled_Account;
	}
	
	public String sacarSaldo(Cuenta_Fintech cf) {
		if(esSegregada(cf)) {
			Cuenta_Referencia cr=((Segregada)cf).getCr();
			return df.format(cr.getSaldo()).toString()+" "+cr.getDiv().getAbreviatura();
		}
		return "Saldo no disponible";
	}
	
	public String seleccionarCuenta(Cuenta_Fintech cf) {
		sesion.setCf(cf);
		
		return "infoCuentas.xhtml";
	}
	
	public String sacarMoneda(Cuenta_Referencia cr) {
		return cr.getDiv().getAbreviatura();
	}
	
	public List<Depositado_en> sacarDeps(){
		if(sesion.getCf() instanceof Pooled_Account) {
			return ((Pooled_Account) sesion.getCf()).getDeps();
		}
		return new ArrayList<>();
	}
	
	public String cambiarDivisa() {
		try {
			tr.setOrigen(sesion.getCf());
			tr.setDestino(sesion.getCf());
			tr.setComision(0.0);
			Cuenta_Referencia crOr=new Cuenta_Referencia();
			crOr.setIBAN(depOr.split(";")[1]);
			tr.setDivEm(((Cuenta_Referencia)gc.sacarCuenta(crOr)).getDiv());
			Cuenta_Referencia crDest=new Cuenta_Referencia();
			crDest.setIBAN(depDest.split(";")[1]);
			tr.setDivEm(((Cuenta_Referencia)gc.sacarCuenta(crDest)).getDiv());
			tr.setTipo("Cambio de divisa");
			tr.setFechaInstruccion(new Date());//-------------------
			Depositado_en_PK depOrPK=new Depositado_en_PK();
			depOrPK.setPaID(depOr.split(";")[0]);
			depOrPK.setCrID(depOr.split(";")[1]);
			Depositado_en depOr=new Depositado_en();
			depOr.setId(depOrPK);
			Depositado_en_PK depDestPK=new Depositado_en_PK();
			depDestPK.setPaID(depDest.split(";")[0]);
			depDestPK.setCrID(depDest.split(";")[1]);
			Depositado_en depDest=new Depositado_en();
			depDest.setId(depDestPK);
			transacciones.cambioDivisa(tr, depOr, depDest);
			this.tr=new Transaccion();
			this.depOr=new String();
			this.depDest=new String();
			return "infoCuentas.xhtml";
		} catch (TransaccionYaExisteException e) {

		} catch (DepositoNoExisteException e) {

		} catch (CuentasNoIgualesException e) {

		} catch (SaldoInsuficianteException e) {

		} catch (CuentaNoExisteException e) {
			// TODO Auto-generated catch block
		} catch (CuentaNoSuporteadaException e) {
			// TODO Auto-generated catch block

		}
		
		return null;
	}
	
	public List<Autorizacion> sacarAutorizaciones(){
		List<Autorizacion> l=new ArrayList<>();
		for(Autorizacion au:sesion.getPa().getAutorizaciones()) {
			if(!au.getEm().getEstado().equalsIgnoreCase("BAJA")) {
				l.add(au);
			}
		}
		return l;
	}
	
	public boolean esBlq(Empresa em){
		return em.getEstado().equalsIgnoreCase("BLOQUEADO");
	}
	
	public List<Cuenta_Fintech> sacarCuentas(Cliente cl){
		List<Cuenta_Fintech> l=new ArrayList<>();
		for(Cuenta_Fintech cf:cl.getCf()) {
			if(!cf.getEstado().equalsIgnoreCase("BAJA")) {
				l.add(cf);
			}
		}
		return l;
	}
	
	public List<Transaccion> sacarTr(){
		List<Transaccion> l=new ArrayList<>();
		try {
			for(Transaccion t:transacciones.sacarTransacciones(sesion.getCf())){
				if(!l.contains(t)) {
					l.add(t);
				}
			}
			l.sort((o1, o2) ->{
				
				return o2.getFechaInstruccion().compareTo(o1.getFechaInstruccion());
				
			});
		} catch (CuentaNoExisteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return l;
	}
	
	public boolean esIngreso(Transaccion t) {
		return t.getDestino().equals(sesion.getCf());
	}
	
	public String formatSaldo(Double d) {
		return df.format(d);
	}
	
}
