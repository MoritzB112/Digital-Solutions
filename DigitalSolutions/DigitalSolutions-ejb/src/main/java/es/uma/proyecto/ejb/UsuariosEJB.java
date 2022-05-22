package es.uma.proyecto.ejb;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.ejb.Excepciones.ContraseñaIncorrectaException;
import es.uma.proyecto.ejb.Excepciones.PasswordException;
import es.uma.proyecto.ejb.Excepciones.UsuarioExistenteException;
import es.uma.proyecto.ejb.Excepciones.UsuarioNoEncontradoException;
import es.uma.proyecto.jpa.Usuario;

@Stateless
public class UsuariosEJB implements GestionUsuarios {

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;

	public Boolean esAdministrativo(Usuario u) throws UsuarioNoEncontradoException {
		Usuario c = em.find(Usuario.class, u.getUsuario());
		if (c == null) {
			throw new UsuarioNoEncontradoException();
		}

		return c.getEsAdministrativo();
	}

	public void crearUsuario(Usuario u) throws UsuarioExistenteException, PasswordException {

		if (em.find(Usuario.class, u.getUsuario()) != null) {
			throw new UsuarioExistenteException();
		}

//		// Generamos la salt
//		byte[] salt = new byte[16];
//		SecureRandom sm = new SecureRandom();
//		sm.nextBytes(salt);
//		
//		u.setSalt(salt);

//		u.setPassword(hashPassword(u.getPassword(), salt));
		
		em.persist(u);
	}

//	private byte[] hashPassword(byte[] password, byte[] salt) throws PasswordException {
//		try {
//			MessageDigest mg = MessageDigest.getInstance("SHA-256");
//			mg.update(salt);
//			
//			return mg.digest(password);
//
//		} catch (Exception e) {
//			throw new PasswordException();
//		}
//	}

	public Usuario usuarioRegistrado(String usuario, String contraseña)
			throws PasswordException, UsuarioNoEncontradoException, ContraseñaIncorrectaException {
		List<Usuario> l = em.createQuery("SELECT us FROM Usuario us", Usuario.class).getResultList();

		for (Usuario usu : l) {
			if (usuario.equals(usu.getUsuario())) {
//				if (comprobarContraseña(usu, contraseña)) {
				if(contraseña.equals(usu.getPassword())) {

					return usu;

				} else {

					throw new ContraseñaIncorrectaException();

				}
			}
		}

		throw new UsuarioNoEncontradoException();
	}

//	private boolean comprobarContraseña(Usuario u, String contraseña) throws PasswordException {
//		try {
//			MessageDigest mg = MessageDigest.getInstance("SHA-256");
//			mg.update(u.getSalt());
//
//			return MessageDigest.isEqual(mg.digest(contraseña.getBytes()), u.getPassword());
//
//		} catch (Exception e) {
//			throw new PasswordException();
//		}
//	}

	public List<Usuario> sacarUsuarios(){
		return em.createQuery("SELECT usu FROM Usuario usu", Usuario.class).getResultList();
	}
}