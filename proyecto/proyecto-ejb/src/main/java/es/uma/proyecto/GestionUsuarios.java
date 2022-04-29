package es.uma.proyecto;

import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.Excepciones.ContraseñaIncorrectaException;
import es.uma.proyecto.Excepciones.PasswordException;
import es.uma.proyecto.Excepciones.UsuarioExistenteException;
import es.uma.proyecto.Excepciones.UsuarioNoEncontradoException;

@Local
public interface GestionUsuarios {

	public Boolean esAdministrativo(Usuario user) throws UsuarioNoEncontradoException;

	public void crearUsuario(Usuario u) throws UsuarioExistenteException, PasswordException;
	
	public Usuario usuarioRegistrado(String usuario, String contraseña) throws PasswordException, UsuarioNoEncontradoException, ContraseñaIncorrectaException;
	
	public List<Usuario> sacarUsuarios();
	
}