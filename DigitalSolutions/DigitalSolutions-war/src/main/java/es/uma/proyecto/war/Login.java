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

    public String entrar() {
        try {
            usuario=cuenta.usuarioRegistrado(usuario.getUsuario(),usuario.getPassword());
            sesion.setUsuario(usuario);
//            if(cuenta.esAdministrativo(usuario)) {
//            	return "adminView.xhtml";
//            }
//            if(sesion.esPa()) {
//            	return "personaAutorizadaView.xhtml";
//            }
            return "clientView.xhtml";

        }catch (UsuarioNoEncontradoException e) {
        	 FacesMessage fm = new FacesMessage("La cuenta no existe");
             FacesContext.getCurrentInstance().addMessage("login:user", fm);
		}catch (ContraseñaIncorrectaException e) {
			 FacesMessage fm = new FacesMessage("La contrasena es incorrecta");
	         FacesContext.getCurrentInstance().addMessage("login:user", fm);
		}catch (PasswordException e) {
			 FacesMessage fm = new FacesMessage("La contrasena es incorrecta");
	         FacesContext.getCurrentInstance().addMessage("login:user", fm);
		}
        return null;
    }

}
