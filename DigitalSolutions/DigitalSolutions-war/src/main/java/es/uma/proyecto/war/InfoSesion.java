/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.proyecto.war;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.ejb.GestionUsuarios;
import es.uma.proyecto.jpa.Cliente;
import es.uma.proyecto.jpa.Cuenta;
import es.uma.proyecto.jpa.Cuenta_Fintech;
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
	private Cliente client;
	private Cuenta_Fintech cf;

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

	public Cliente getClient() {
		return client;
	}

	public void setClient(Cliente client) {
		this.client = client;
	}

	public Cuenta_Fintech getCf() {
		return cf;
	}

	public void setCf(Cuenta_Fintech cf) {
		this.cf = cf;
	}

	public synchronized String invalidarSesion() {
		if (usuario != null) {
			usuario = null;
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		}
		return "login.xhtml";
	}
	
	public synchronized boolean esPa() {
		return usuario.getPa()!=null;
	}
	
	public synchronized boolean esId() {
		return usuario.getCl()!=null;
	}
	
	public synchronized List<Cuenta_Fintech> getCuentas() {
//		if() {
//			return 
//		}
		return null;
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
