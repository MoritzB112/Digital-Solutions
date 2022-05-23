package es.uma.proyecto.ejb;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.jpa.Autorizacion;
import es.uma.proyecto.jpa.Autorizacion_PK;
import es.uma.proyecto.jpa.Cuenta_Referencia;
import es.uma.proyecto.jpa.Divisa;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Individual;
import es.uma.proyecto.jpa.Persona_Autorizada;
import es.uma.proyecto.jpa.Pooled_Account;
import es.uma.proyecto.jpa.Segregada;
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
		a.setEsAdministrativo(false);
		a.setSalt(parseString(getSalt()));
		a.setPassword(parseString(hashPassword(parseByte(a.getSalt()), "juan".getBytes())));
		a.setUsuario("juan");
		em.persist(a);
		
		Usuario b = new Usuario();
		b.setEsAdministrativo(false);
		b.setSalt(parseString(getSalt()));
		b.setPassword(parseString(hashPassword(parseByte(b.getSalt()), "ana".getBytes())));
		b.setUsuario("ana");
		em.persist(b);
		
		Individual ind = new Individual();
		ind.setIdentificacion("63937528N");
		ind.setId(2L);
		ind.setNombre("Juan");
		ind.setApellido("Pérez");
		ind.setUs(a);
		em.persist(ind);
		
		Empresa emp = new Empresa();
		emp.setIdentificacion("P3310693A");
		emp.setId(1L);
		emp.setTipo_cliente("JURÍDICA");
		emp.setEstado("ACTIVA");
		emp.setFecha_alta(new Date());
		emp.setDireccion("Avenida del Cid");
		emp.setCiudad("Granada");
		emp.setCodigoPostal(29000);
		emp.setPais("España");
		emp.setRazon_social("TECHFUTURE");
		em.persist(emp);
		
		Persona_Autorizada pa = new Persona_Autorizada();
		pa.setID(3L);
		pa.setIdentificacion("Y4001267V");
		pa.setApellidos("García");
		pa.setNombre("Andrea");
		pa.setDireccion("Plaza Alta");
		pa.setCiudad("Madrid");
		pa.setCodigoPostal(21000);
		pa.setPais("España");
		pa.setUs(b);
		em.persist(pa);
		
		Autorizacion au = new Autorizacion();
		Autorizacion_PK aupk = new Autorizacion_PK();
		aupk.setEmID(emp.getId());
		aupk.setPaID(pa.getID());
		au.setEm(emp);
		au.setId(aupk);
		au.setPa(pa);
		au.setTipo("AUTORIZADO");
		
		
		Divisa div = new Divisa();
		div.setNombre("Dolares");
		div.setAbreviatura("USD");
		div.setCambioEuro(2.0);
		div.setSimbolo("$");
		
		Cuenta_Referencia cr = new Cuenta_Referencia();
		cr.setIBAN("VG57DDVS5173214964983931");
		cr.setDiv(div);
		cr.setEstado("ACTIVA");
		cr.setNombreBanco("SANTANDER");
		cr.setSaldo(24351.15);
		
		Cuenta_Referencia cr2 = new Cuenta_Referencia();
		cr2.setIBAN("HN47QUXH11325678769785549996");
		cr2.setDiv(div);
		cr2.setEstado("ACTIVA");
		cr2.setNombreBanco("UNICAJA");
		cr2.setSaldo(54671.15);
		
		Segregada se = new Segregada();
		se.setIBAN("NL63ABNA6548268733");
		se.setCl(emp);
		se.setEstado("ACTIVA");
		se.setFecha_apertura(new Date());
		se.setCr(cr);
		
		
		Segregada se2 = new Segregada();
		se2.setIBAN("FR5514508000502273293129K55");
		se2.setEstado("ACTIVA");
		se2.setCl(emp);
		se2.setFecha_apertura(new Date());
		se2.setCr(cr2);
		
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, 9);
		cal.set(Calendar.YEAR, 2021);
		Segregada se3 = new Segregada();
		se3.setIBAN("DE31500105179261215675");
		se3.setCl(emp);
		se3.setEstado("CERRADA");
		se3.setFecha_apertura(new Date());
		se3.setFecha_cierre(cal.getTime());
		
		Pooled_Account pooA = new Pooled_Account();
		pooA.setIBAN("ES8400817251647192321264");
		
		
		
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
