package es.uma.proyecto.war;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.ejb.GestionUsuarios;

@Named(value = "administrativo")
@RequestScoped
public class Administrativo {
	
    @Inject
	private GestionUsuarios cuenta;

    @Inject
    private InfoSesion sesion;

}
