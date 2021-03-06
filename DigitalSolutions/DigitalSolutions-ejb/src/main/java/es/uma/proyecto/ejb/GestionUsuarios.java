package es.uma.proyecto.ejb;

import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.ejb.Excepciones.Contrase├▒aIncorrectaException;
import es.uma.proyecto.ejb.Excepciones.PasswordException;
import es.uma.proyecto.ejb.Excepciones.UsuarioExistenteException;
import es.uma.proyecto.ejb.Excepciones.UsuarioNoEncontradoException;
import es.uma.proyecto.jpa.Usuario;

@Local
public interface GestionUsuarios {

	public Boolean esAdministrativo(Usuario user) throws UsuarioNoEncontradoException;

	public void crearUsuario(Usuario u) throws UsuarioExistenteException, PasswordException;
	
	public Usuario usuarioRegistrado(String usuario, String contrase├▒a) throws PasswordException, UsuarioNoEncontradoException, Contrase├▒aIncorrectaException;
	
	public List<Usuario> sacarUsuarios();
	
	public void actualizarUsuario(Usuario u) throws UsuarioNoEncontradoException;
	
}