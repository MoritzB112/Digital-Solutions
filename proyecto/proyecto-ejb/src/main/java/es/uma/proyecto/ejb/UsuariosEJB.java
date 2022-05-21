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

		// Generamos la salt
		byte[] salt = new byte[16];
		SecureRandom sm = new SecureRandom();
		sm.nextBytes(salt);
		
		u.setSalt(Base64.getEncoder().encodeToString(salt));

		u.setPassword(hashPassword(u.getPassword(), salt));
		
		em.persist(u);
	}

	private String hashPassword(String password, byte[] salt) throws PasswordException {
		try {
			MessageDigest mg = MessageDigest.getInstance("SHA-256");
			mg.update(salt);
			byte[] hashed=mg.digest(Base64.getDecoder().decode(password));
			
			return Base64.getEncoder().encodeToString(hashed);

		} catch (Exception e) {
			throw new PasswordException();
		}
	}

	public Usuario usuarioRegistrado(String usuario, String contraseña)
			throws PasswordException, UsuarioNoEncontradoException, ContraseñaIncorrectaException {
		List<Usuario> l = em.createQuery("SELECT us FROM Usuario us", Usuario.class).getResultList();

		for (Usuario usu : l) {
			if (usuario.equals(usu.getUsuario())) {
				if (comprobarContraseña(usu, contraseña)) {

					return usu;

				} else {

					throw new ContraseñaIncorrectaException();

				}
			}
		}

		throw new UsuarioNoEncontradoException();
	}

	private boolean comprobarContraseña(Usuario u, String contraseña) throws PasswordException {
		try {
			byte[] salt = Base64.getDecoder().decode(u.getSalt());
			MessageDigest mg = MessageDigest.getInstance("SHA-256");
			mg.update(salt);
			byte[] hashed=mg.digest(Base64.getDecoder().decode(contraseña));
			return (u.getPassword().equals(Base64.getEncoder().encodeToString(hashed)));

		} catch (Exception e) {
			throw new PasswordException();
		}
	}

	public List<Usuario> sacarUsuarios(){
		return em.createQuery("SELECT usu FROM Usuario usu", Usuario.class).getResultList();
	}
}