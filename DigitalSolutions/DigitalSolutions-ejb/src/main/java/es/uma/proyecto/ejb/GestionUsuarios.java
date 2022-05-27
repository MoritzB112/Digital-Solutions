package es.uma.proyecto.ejb;

import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.ejb.Excepciones.ContraseñaIncorrectaException;
import es.uma.proyecto.ejb.Excepciones.PasswordException;
import es.uma.proyecto.ejb.Excepciones.UsuarioExistenteException;
import es.uma.proyecto.ejb.Excepciones.UsuarioNoEncontradoException;
import es.uma.proyecto.jpa.Usuario;

@Local
public interface GestionUsuarios {

	public Boolean esAdministrativo(Usuario user) throws UsuarioNoEncontradoException;

	public void crearUsuario(Usuario u) throws UsuarioExistenteException, PasswordException;
	
	public Usuario usuarioRegistrado(String usuario, String contraseña) throws PasswordException, UsuarioNoEncontradoException, ContraseñaIncorrectaException;
	
	public List<Usuario> sacarUsuarios();
	
	public void actualizarUsuario(Usuario u) throws UsuarioNoEncontradoException;
	
}