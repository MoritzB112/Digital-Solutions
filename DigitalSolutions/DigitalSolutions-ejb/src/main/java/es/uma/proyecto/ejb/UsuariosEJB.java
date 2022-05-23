package es.uma.proyecto.ejb;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
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
		
		u.setSalt(parseString(salt));

		u.setPassword(parseString(hashPassword(salt, u.getPassword().getBytes())));
		
		em.persist(u);
	}

	public Usuario usuarioRegistrado(String usuario, String contraseña)
			throws PasswordException, UsuarioNoEncontradoException, ContraseñaIncorrectaException {
		List<Usuario> l = em.createQuery("SELECT us FROM Usuario us", Usuario.class).getResultList();

		for (Usuario usu : l) {
			if (usuario.equals(usu.getUsuario())) {
				if (comprobarPw(usu, contraseña)) {

					return usu;

				} else {

					throw new ContraseñaIncorrectaException();

				}
			}
		}

		throw new UsuarioNoEncontradoException();
	}

	public List<Usuario> sacarUsuarios(){
		return em.createQuery("SELECT usu FROM Usuario usu", Usuario.class).getResultList();
	}
	
	private static byte[] hashPassword(byte[] salt, byte[] password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(salt);
			return digest.digest(password);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	private static boolean comprobarPw(Usuario u, String pw) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(parseByte(u.getSalt()));
			return MessageDigest.isEqual(parseByte(u.getPassword()), digest.digest(pw.getBytes()));

		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	private static String parseString(byte[] ba) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ba.length; i++) {
			sb.append(Integer.toString((ba[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

	private static byte[] parseByte(String str) {
		byte[] ans = new byte[str.length() / 2];
		for (int i = 0; i < ans.length; i++) {
			int index = i * 2;
			int val = Integer.parseInt(str.substring(index, index + 2), 16);
			ans[i] = (byte) val;
		}

		return ans;
	}
}