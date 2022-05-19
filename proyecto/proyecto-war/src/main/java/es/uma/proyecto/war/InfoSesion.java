/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.proyecto.war;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.ejb.GestionUsuarios;
import es.uma.proyecto.jpa.Usuario;

/**
 *
 * @author francis
 */
@Named(value = "infoSesion")
@SessionScoped
public class InfoSesion implements Serializable {

	@Inject
	private GestionUsuarios cuenta;
	private Usuario usuario;

	/**
	 * Creates a new instance of InfoSesion
	 */
	public InfoSesion() {
	}

	public synchronized void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public synchronized Usuario getUsuario() {
		return usuario;
	}

	public synchronized String invalidarSesion() {
		if (usuario != null) {
			usuario = null;
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		}
		return "login.xhtml";
	}

//    public synchronized void refrescarUsuario()
//    {
//        try {
//        if (usuario != null)
//        {
//            usuario = cuenta.refrescarUsuario(usuario);
//            System.out.println(usuario.getContactos().size());
//        } 
//        }
//        catch (AgendaException e) {
//            // TODO
//        }
//    }

}
