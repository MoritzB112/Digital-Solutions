package es.uma.proyecto.ejb;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.jpa.Autorizacion;
import es.uma.proyecto.jpa.Autorizacion_PK;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Persona_Autorizada;
import es.uma.proyecto.jpa.Usuario;

@Singleton
@Startup
public class InicializaBBDD {

	private static SecureRandom sm = new SecureRandom();

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;

	@PostConstruct
	public void inicializar() {

		if (em.find(Usuario.class, "testU") != null) {
			return;
		}

		Usuario u = new Usuario();
		u.setCorreo("testU@test.test");
		u.setEsAdministrativo(false);
		u.setSalt(parseString(getSalt()));
		u.setPassword(parseString(hashPassword(parseByte(u.getSalt()), "test".getBytes())));
		u.setUsuario("testU");
		em.persist(u);

		Usuario a = new Usuario();
		a.setCorreo("testU@admin.test");
		a.setEsAdministrativo(true);
		a.setSalt(parseString(getSalt()));
		a.setPassword(parseString(hashPassword(parseByte(a.getSalt()), "test".getBytes())));
		a.setUsuario("admin");
		em.persist(a);

		Persona_Autorizada pa = new Persona_Autorizada();
		pa.setID(1L);
		pa.setNombre("alguno");
		pa.setApellidos("garcia");
		pa.setDireccion("pozo");
		pa.setCiudad("malaga");
		pa.setCodigoPostal(29010);
		pa.setUs(u);
		pa.setPais("España");
		em.persist(pa);

		Empresa emp = new Empresa();
		emp.setIdentificacion("algunString");
		emp.setId(1L);
		emp.setTipo_cliente("juridica");
		emp.setEstado("activa");
		emp.setFecha_alta(new Date());
		emp.setDireccion("direccion");
		emp.setCiudad("granada");
		emp.setCodigoPostal(29000);
		emp.setPais("España");
		emp.setRazon_social("trabajador");
		em.persist(emp);

		Autorizacion au = new Autorizacion();
		Autorizacion_PK aupk = new Autorizacion_PK();
		aupk.setEmID(1L);
		aupk.setPaID(1L);
		au.setId(aupk);
		au.setTipo("prueba");
		au.setEm(emp);
		au.setPa(pa);
		em.persist(au);
	}

	private byte[] hashPassword(byte[] salt, byte[] password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(salt);
			return digest.digest(password);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	private boolean comprobarPw(Usuario u, String pw) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(parseByte(u.getSalt()));
			return MessageDigest.isEqual(parseByte(u.getPassword()), digest.digest(pw.getBytes()));

		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	private String parseString(byte[] ba) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ba.length; i++) {
			sb.append(Integer.toString((ba[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

	private byte[] parseByte(String str) {
		byte[] ans = new byte[str.length() / 2];
		for (int i = 0; i < ans.length; i++) {
			int index = i * 2;
			int val = Integer.parseInt(str.substring(index, index + 2), 16);
			ans[i] = (byte) val;
		}

		return ans;
	}

	private byte[] getSalt() {
		byte[] salt = new byte[16];
		sm.nextBytes(salt);

		return salt;
	}

}
