package es.uma.proyecto.war;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.jpa.Empresa;

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
}
