package es.uma.proyecto.war;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.ejb.GestionTransacciones;
import es.uma.proyecto.ejb.Excepciones.CuentasNoIgualesException;
import es.uma.proyecto.ejb.Excepciones.DepositoNoExisteException;
import es.uma.proyecto.ejb.Excepciones.SaldoInsuficianteException;
import es.uma.proyecto.ejb.Excepciones.TransaccionYaExisteException;
import es.uma.proyecto.jpa.Cuenta_Fintech;
import es.uma.proyecto.jpa.Cuenta_Referencia;
import es.uma.proyecto.jpa.Depositado_en;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Pooled_Account;
import es.uma.proyecto.jpa.Segregada;
import es.uma.proyecto.jpa.Transaccion;

@Named(value = "infoCuentas")
@RequestScoped
public class InfoCuentas implements Serializable {

	@Inject
    private InfoSesion sesion;
	
	@EJB
	private GestionTransacciones transacciones;
	
	private Transaccion tr;
	private Depositado_en depOr;
	private Depositado_en depDest;
	
	public InfoCuentas() {
		tr=new Transaccion();
		depOr=new Depositado_en();
		depDest=new Depositado_en();
	}
	
	
	
	public Depositado_en getDepOr() {
		return depOr;
	}



	public void setDepOr(Depositado_en depOr) {
		this.depOr = depOr;
	}



	public Depositado_en getDepDest() {
		return depDest;
	}



	public void setDepDest(Depositado_en depDest) {
		this.depDest = depDest;
	}



	public Transaccion getTr() {
		return tr;
	}



	public void setTr(Transaccion tr) {
		this.tr = tr;
	}



	public String elegirCuenta() {
//		sesion.setClient(em);
		return "clientView.xhtml";
	}
	
	public String elegirEmpresa(Empresa em) {
		
		sesion.setEm(em);
		
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
			return cr.getSaldo().toString()+" "+cr.getDiv().getAbreviatura();
		}
		return "No disponible";
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
			tr.setDivEm(depOr.getCr().getDiv());
			tr.setDivRec(depDest.getCr().getDiv());
			tr.setTipo("Cambio de divisa");
			tr.setFechaInstruccion(new Date());
			transacciones.cambioDivisa(tr, depOr, depDest);
		} catch (TransaccionYaExisteException e) {

		} catch (DepositoNoExisteException e) {

		} catch (CuentasNoIgualesException e) {

		} catch (SaldoInsuficianteException e) {

		}
		
		
		return null;
	}
}
