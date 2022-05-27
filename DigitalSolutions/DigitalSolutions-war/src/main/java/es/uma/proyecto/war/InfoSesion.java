/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package es.uma.proyecto.war;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import es.uma.proyecto.ejb.GestionGenerarReportes;
import es.uma.proyecto.ejb.GestionRefresh;
import es.uma.proyecto.ejb.GestionUsuarios;
import es.uma.proyecto.ejb.Excepciones.ClienteNoExisteException;
import es.uma.proyecto.ejb.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.Persona_AutorizadaNoEncontradaException;
import es.uma.proyecto.ejb.Excepciones.UsuarioNoEncontradoException;
import es.uma.proyecto.jpa.Cuenta_Fintech;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Individual;
import es.uma.proyecto.jpa.Persona_Autorizada;
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

	@Inject
	private GestionRefresh refresh;
	
	@Inject
	private GestionGenerarReportes reporte;

	private Usuario usuario;
	private Individual id;
	private Empresa em;
	private Persona_Autorizada pa;
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

	public synchronized Individual getId() {
		return id;
	}

	public synchronized void setId(Individual id) {
		this.id = id;
	}

	public synchronized Empresa getEm() {
		return em;
	}

	public synchronized void setEm(Empresa em) {
		this.em = em;
	}

	public synchronized Persona_Autorizada getPa() {
		return pa;
	}

	public synchronized void setPa(Persona_Autorizada pa) {
		this.pa = pa;
	}

	public synchronized Cuenta_Fintech getCf() {
		return cf;
	}

	public synchronized void setCf(Cuenta_Fintech cf) {
		this.cf = cf;
	}

	public synchronized String invalidarSesion() {
		if (usuario != null) {
			usuario = null;
			id = null;
			em = null;
			pa = null;
			cf = null;

			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		}
		return "index.xhtml";
	}

	public synchronized boolean esPa() {
		return pa != null;
	}

	public synchronized boolean esId() {
		return id != null;
	}

	public synchronized boolean esEm() {
		return em != null;
	}

	public synchronized void refreshAll() {
		if (usuario != null) {
			try {
				usuario = refresh.refUsu(usuario);
			} catch (UsuarioNoEncontradoException e) {

			}
		}
		if (id != null) {
			try {
				id = refresh.refInd(id);
			} catch (ClienteNoExisteException e) {
				
			}
		}
		if(em!=null) {
			try {
				em = refresh.refEmp(em);
			} catch (ClienteNoExisteException e) {
				
			}
		}
		if(pa!=null) {
			try {
				pa = refresh.refPa(pa);
			} catch (Persona_AutorizadaNoEncontradaException e) {
				
			}
		}
		if(cf!=null) {
			try {
				cf = refresh.refCf(cf);
			} catch (CuentaNoExisteException e) {
				
			}
		}
	}
	
	public String aux() {
		return "</tr><tr>";
	}
	
	public void download() {
		File file;
		try {
			file = new File(reporte.generarReportePrimero());
			  Faces.sendFile(file, true);
			  
		} catch (IOException e) {
			
		}
	  
	}

}
