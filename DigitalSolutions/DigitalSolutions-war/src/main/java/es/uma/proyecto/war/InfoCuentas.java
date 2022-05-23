package es.uma.proyecto.war;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.jpa.Cuenta_Fintech;
import es.uma.proyecto.jpa.Cuenta_Referencia;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Pooled_Account;
import es.uma.proyecto.jpa.Segregada;

@Named(value = "infoCuentas")
@RequestScoped
public class InfoCuentas implements Serializable {

	@Inject
    private InfoSesion sesion;
	
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
}
