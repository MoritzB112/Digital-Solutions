package es.uma.proyecto;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.Excepciones.ContraseñaIncorrectaException;
import es.uma.proyecto.Excepciones.PasswordException;
import es.uma.proyecto.Excepciones.UsuarioExistenteException;
import es.uma.proyecto.Excepciones.UsuarioNoEncontradoException;

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
		u.setSalt(new String(salt));

		u.setPassword(hashPassword(u.getPassword(), salt));

		em.persist(u);
	}

	private String hashPassword(String password, byte[] salt) throws PasswordException {
		ByteArrayOutputStream contra = new ByteArrayOutputStream();
		try {
			contra.write(password.getBytes());
			contra.write(salt);
			MessageDigest mg = MessageDigest.getInstance("SHA-256");

			return new String(mg.digest(contra.toByteArray()));

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
			byte[] salt = u.getSalt().getBytes();
			ByteArrayOutputStream contra = new ByteArrayOutputStream();
			contra.write(contraseña.getBytes());
			contra.write(salt);
			MessageDigest mg = MessageDigest.getInstance("SHA-256");

			return (u.getPassword().equals(new String(mg.digest(contra.toByteArray()))));

		} catch (Exception e) {
			throw new PasswordException();
		}
	}


	public List<Usuario> sacarUsuario(){
		return em.createQuery("SELECT usu FROM Usuario usu", Usuario.class).getResultList();
	}
}