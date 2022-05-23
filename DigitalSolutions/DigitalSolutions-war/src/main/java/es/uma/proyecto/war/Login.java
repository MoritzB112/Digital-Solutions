/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uma.proyecto.war;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.ejb.GestionCuentas;
import es.uma.proyecto.ejb.GestionUsuarios;
import es.uma.proyecto.ejb.Excepciones.ContraseñaIncorrectaException;
import es.uma.proyecto.ejb.Excepciones.PasswordException;
import es.uma.proyecto.ejb.Excepciones.UsuarioNoEncontradoException;
import es.uma.proyecto.jpa.Individual;
import es.uma.proyecto.jpa.Persona_Autorizada;
import es.uma.proyecto.jpa.Usuario;

/**
 *
 * @author francis
 */
@Named(value = "login")
@RequestScoped
public class Login {

	@Inject
	private GestionUsuarios cuenta;

	@Inject
	private InfoSesion sesion;

	private Usuario usuario;
	private String userName;
	private String password;

	/**
	 * Creates a new instance of login
	 */
	public Login() {
		usuario = new Usuario();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String entrar() {
		try {
			usuario = cuenta.usuarioRegistrado(getUserName(), getPassword());
			if(cuenta.esAdministrativo(usuario)) {
				FacesMessage fm = new FacesMessage("La cuenta no existe");
				FacesContext.getCurrentInstance().addMessage("userMessage:user", fm);
				usuario=new Usuario();
				return null;
			}
			sesion.setUsuario(usuario);
			if (usuario.getPa() != null) {
				sesion.setPa(usuario.getPa());
				return "personaAutorizadaView.xhtml";
			} else if (usuario.getCl() != null) {
				sesion.setId((Individual) usuario.getCl());
				return "clientView.xhtml";
			}
		} catch (UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("La cuenta no existe");
			FacesContext.getCurrentInstance().addMessage("userMessage:user", fm);
		} catch (ContraseñaIncorrectaException e) {
			FacesMessage fm = new FacesMessage("La contrasena es incorrecta");
			FacesContext.getCurrentInstance().addMessage("userMessage:user", fm);
		} catch (PasswordException e) {
			FacesMessage fm = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage("userMessage:user", fm);
		}
		return null;
	}

	public String entrarAD() {
		try {
			usuario = cuenta.usuarioRegistrado(getUserName(), getPassword());
			if (!cuenta.esAdministrativo(usuario)) {
				FacesMessage fm = new FacesMessage("La cuenta no existe");
				FacesContext.getCurrentInstance().addMessage("userMessage:user", fm);
				return null;
			} 
			sesion.setUsuario(usuario);
			return "adminView.xhtml";

		} catch (UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("La cuenta no existe");
			FacesContext.getCurrentInstance().addMessage("userMessage:user", fm);
		} catch (ContraseñaIncorrectaException e) {
			FacesMessage fm = new FacesMessage("La contrasena es incorrecta");
			FacesContext.getCurrentInstance().addMessage("userMessage:user", fm);
		} catch (PasswordException e) {
			FacesMessage fm = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage("userMessage:user", fm);
		}
		return null;
	}

}
