package es.uma.informatica.sii.ejb.practica;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.proyecto.jpa.Autorizacion;
import es.uma.proyecto.jpa.Autorizacion_PK;
import es.uma.proyecto.jpa.Cuenta_Referencia;
import es.uma.proyecto.jpa.Depositado_en;
import es.uma.proyecto.jpa.Depositado_en_PK;
import es.uma.proyecto.jpa.Divisa;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Individual;
import es.uma.proyecto.jpa.Persona_Autorizada;
import es.uma.proyecto.jpa.Pooled_Account;
import es.uma.proyecto.jpa.Segregada;
import es.uma.proyecto.jpa.Transaccion;
import es.uma.proyecto.jpa.Usuario;

public class BaseDatos {
	public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// Divisas de ejemplo
		Divisa d1 = new Divisa();
		d1.setAbreviatura("EUR");
		d1.setCambioEuro(1.0);
		d1.setNombre("EURO");
		em.persist(d1);
		Divisa d2 = new Divisa();
		d2.setAbreviatura("USD");
		d2.setCambioEuro(1.6);
		d2.setNombre("US-DOLLAR");
		em.persist(d2);

		// CR de ejemplo
		Cuenta_Referencia cr = new Cuenta_Referencia();
		cr.setIBAN("IBANTESTCR1");
		cr.setDeps(new ArrayList<>());
		cr.setCobros(new ArrayList<>());
		cr.setPagos(new ArrayList<>());
		cr.setEstado("ALTA");
		cr.setSaldo(1.0);
		cr.setDiv(d1);
		cr.setNombreBanco("BANCOTEST");
		em.persist(cr);
		Cuenta_Referencia cr2 = new Cuenta_Referencia();
		cr2.setIBAN("IBANTESTCR2");
		cr2.setDeps(new ArrayList<>());
		cr2.setCobros(new ArrayList<>());
		cr2.setPagos(new ArrayList<>());
		cr2.setEstado("ALTA");
		cr2.setSaldo(0.0);
		cr2.setNombreBanco("BANCOTEST");
		cr2.setDiv(d2);
		em.persist(cr2);
		Cuenta_Referencia cr3 = new Cuenta_Referencia();
		cr3.setIBAN("IBANTESTCR3");
		cr3.setDeps(new ArrayList<>());
		cr3.setCobros(new ArrayList<>());
		cr3.setPagos(new ArrayList<>());
		cr3.setEstado("ALTA");
		cr3.setSaldo(0.0);
		cr3.setNombreBanco("BANCOTEST");
		cr3.setDiv(d1);
		em.persist(cr3);

		// Usuario de ejemplo
		Usuario u = new Usuario();
		u.setEsAdministrativo(false);
		u.setUsuario("testPA1");
		u.setPassword(hashPassword("testPA1", "SAL"));
		u.setSalt("SAL");
		em.persist(u);
		Usuario u2 = new Usuario();
		u2.setEsAdministrativo(false);
		u2.setUsuario("testPA2");
		u2.setPassword(hashPassword("testPA2", "SAL"));
		u2.setSalt("SAL");
		em.persist(u2);
		Usuario u3 = new Usuario();
		u3.setEsAdministrativo(false);
		u3.setUsuario("testID3");
		u3.setPassword(hashPassword("testID3", "SAL"));
		u3.setSalt("SAL");
		em.persist(u3);
		Usuario u4 = new Usuario();
		u4.setEsAdministrativo(false);
		u4.setUsuario("testID4");
		u4.setPassword(hashPassword("testID4", "SAL"));
		u4.setSalt("SAL");
		em.persist(u4);
		Usuario u5 = new Usuario();
		u5.setEsAdministrativo(true);
		u5.setUsuario("testAD5");
		u5.setPassword(hashPassword("testAD5", "SAL"));
		u5.setSalt("SAL");
		em.persist(u5);

		// Empresa de ejemplo
		Empresa emp = new Empresa();
		emp.setId(1L);
		emp.setTipo_cliente("JUDIRICA");
		emp.setRazon_social("X");
		emp.setEstado("ALTA");
		emp.setFecha_alta(new Date());
		emp.setDireccion("X");
		emp.setPais("X");
		emp.setCodigoPostal(1);
		emp.setCiudad("X");
		emp.setAu(new ArrayList<>());
		emp.setCf(new ArrayList<>());
		em.persist(emp);
		Empresa emp2 = new Empresa();
		emp2.setId(2L);
		emp2.setTipo_cliente("JUDIRICA");
		emp2.setRazon_social("X");
		emp2.setEstado("ALTA");
		emp2.setFecha_alta(new Date());
		emp2.setDireccion("X");
		emp2.setPais("X");
		emp2.setCodigoPostal(1);
		emp2.setCiudad("X");
		emp2.setAu(new ArrayList<>());
		emp2.setCf(new ArrayList<>());
		em.persist(emp2);

		// PA de ejemplo
		Persona_Autorizada pa = new Persona_Autorizada();
		pa.setID(1L);
		pa.setNombre("X");
		pa.setApellidos("X");
		pa.setCiudad("X");
		pa.setDireccion("X");
		pa.setEstado("ALTA");
		pa.setPais("X");
		pa.setCodigoPostal(1);
		pa.setUs(u);
		pa.setAutorizaciones(new ArrayList<>());
		em.persist(pa);
		Persona_Autorizada pa2 = new Persona_Autorizada();
		pa2.setID(2L);
		pa2.setNombre("X");
		pa2.setApellidos("X");
		pa2.setCiudad("X");
		pa2.setDireccion("X");
		pa2.setEstado("ALTA");
		pa2.setPais("X");
		pa2.setCodigoPostal(1);
		pa2.setUs(u2);
		pa2.setAutorizaciones(new ArrayList<>());
		em.persist(pa2);

		// AU de ejemplo
		Autorizacion au = new Autorizacion();
		Autorizacion_PK auPK = new Autorizacion_PK();
		auPK.setEmID(emp2.getId());
		auPK.setPaID(pa2.getID());
		au.setId(auPK);
		au.setEm(emp2);
		au.setPa(pa2);
		em.persist(au);
		emp2.getAu().add(au);
		pa2.getAutorizaciones().add(au);

		// Individual de ejemplo
		Individual id = new Individual();
		id.setId(3L);
		id.setTipo_cliente("FISICA");
		id.setUs(u3);
		id.setEstado("ALTA");
		id.setFecha_alta(new Date());
		id.setDireccion("X");
		id.setPais("X");
		id.setCodigoPostal(1);
		id.setCiudad("X");
		id.setNombre("X");
		id.setApellido("X");
		id.setCf(new ArrayList<>());
		em.persist(id);
		Individual id2 = new Individual();
		id2.setId(4L);
		id2.setTipo_cliente("FISICA");
		id2.setUs(u4);
		id2.setEstado("ALTA");
		id2.setFecha_alta(new Date());
		id2.setDireccion("X");
		id2.setPais("X");
		id2.setCodigoPostal(1);
		id2.setCiudad("X");
		id2.setNombre("X");
		id2.setApellido("X");
		id2.setCf(new ArrayList<>());
		em.persist(id2);

		// Segregada de ejemplo
		Segregada se = new Segregada();
		se.setIBAN("IBANTESTSE1");
		se.setCobros(new ArrayList<>());
		se.setPagos(new ArrayList<>());
		se.setCr(cr);
		se.setCl(id);
		se.setEstado("ALTA");
		se.setFecha_apertura(new Date());
		em.persist(se);
		cr.setSe(se);
		id.getCf().add(se);
		Segregada se2 = new Segregada();
		se2.setIBAN("IBANTESTSE2");
		se2.setCobros(new ArrayList<>());
		se2.setPagos(new ArrayList<>());
		se2.setCr(cr2);
		se2.setCl(id);
		se2.setEstado("ALTA");
		se2.setFecha_apertura(new Date());
		em.persist(se2);
		cr2.setSe(se2);
		id.getCf().add(se2);

		// PoAc de ejemplo
		Pooled_Account pac = new Pooled_Account();
		pac.setCl(emp);
		pac.setCobros(new ArrayList<>());
		pac.setPagos(new ArrayList<>());
		pac.setIBAN("IBANTESTPA1");
		pac.setDeps(new ArrayList<>());
		pac.setEstado("ALTA");
		pac.setFecha_apertura(new Date());
		em.persist(pac);
		emp.getCf().add(pac);

		// Transaccion ejemplo
		Transaccion tr = new Transaccion();
		tr.setCantidad(10.0);
		tr.setDestino(pac);
		tr.setOrigen(pac);
		tr.setComision(0.0);
		tr.setDivEm(d1);
		tr.setDivRec(d2);
		tr.setID_unico(1L);
		tr.setFechaInstruccion(new Date());
		tr.setTipo("NORMAL");
		em.persist(tr);

		// Depositado en ejemplo
		Depositado_en de = new Depositado_en();
		Depositado_en_PK dePK = new Depositado_en_PK();
		dePK.setCrID(cr.getIBAN());
		dePK.setPaID(pac.getIBAN());
		de.setCr(cr);
		de.setId(dePK);
		de.setPa(pac);
		de.setSaldo(100.0);
		em.persist(de);

		Depositado_en de2 = new Depositado_en();
		Depositado_en_PK de2PK = new Depositado_en_PK();
		de2PK.setCrID(cr2.getIBAN());
		de2PK.setPaID(pac.getIBAN());
		de2.setCr(cr2);
		de2.setId(dePK);
		de2.setPa(pac);
		de2.setSaldo(0.0);
		em.persist(de2);

		em.getTransaction().commit();

		em.close();
		emf.close();
	}

	public static void inicializarBaseDeDatosPruebaCSV(String nombreUnidadPersistencia) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		// Usuario de ejemplo
		Usuario u = new Usuario();
		u.setEsAdministrativo(false);
		u.setUsuario("testPA1");
		u.setPassword(hashPassword("testPA1", "SAL"));
		u.setSalt("SAL");
		em.persist(u);
		Usuario u2 = new Usuario();
		u2.setEsAdministrativo(false);
		u2.setUsuario("testPA2");
		u2.setPassword(hashPassword("testPA2", "SAL"));
		u2.setSalt("SAL");
		em.persist(u2);
		Usuario u3 = new Usuario();
		u3.setEsAdministrativo(false);
		u3.setUsuario("testID3");
		u3.setPassword(hashPassword("testID3", "SAL"));
		u3.setSalt("SAL");
		em.persist(u3);
		Usuario u4 = new Usuario();
		u4.setEsAdministrativo(false);
		u4.setUsuario("testID4");
		u4.setPassword(hashPassword("testID4", "SAL"));
		u4.setSalt("SAL");
		em.persist(u4);
		Usuario u5 = new Usuario();
		u5.setEsAdministrativo(true);
		u5.setUsuario("testAD5");
		u5.setPassword(hashPassword("testAD5", "SAL"));
		u5.setSalt("SAL");
		em.persist(u5);

		// PA de ejemplo
		Persona_Autorizada pa = new Persona_Autorizada();
		pa.setID(1L);
		pa.setNombre("Prueba1N");
		pa.setApellidos("Prueba1A");
		pa.setCiudad("Prueba1C");
		pa.setDireccion("Prueba1D");
		pa.setEstado("ALTA");
		pa.setPais("Prueba1P");
		pa.setCodigoPostal(2);
		pa.setUs(u);
		pa.setAutorizaciones(new ArrayList<>());
		em.persist(pa);
		Persona_Autorizada pa2 = new Persona_Autorizada();
		pa2.setID(2L);
		pa2.setNombre("Prueba3N");
		pa2.setApellidos("Prueba3A");
		pa2.setCiudad("Prueba3C");
		pa2.setDireccion("Prueba3D");
		pa2.setEstado("ALTA");
		pa2.setPais("Prueba3P");
		pa2.setCodigoPostal(3);
		pa2.setUs(u2);
		pa2.setAutorizaciones(new ArrayList<>());
		em.persist(pa2);

		// Individual ejemplo
		Individual id = new Individual();
		id.setId(3L);
		id.setTipo_cliente("FISICA");
		id.setUs(u3);
		id.setEstado("ALTA");
		id.setFecha_alta(new Date());
		id.setDireccion("X");
		id.setPais("X");
		id.setCodigoPostal(1);
		id.setCiudad("X");
		id.setNombre("X");
		id.setApellido("X");
		id.setCf(new ArrayList<>());
		em.persist(id);
		Individual id2 = new Individual();
		id2.setId(4L);
		id2.setTipo_cliente("FISICA");
		id2.setUs(u4);
		id2.setEstado("ALTA");
		id2.setFecha_alta(new Date());
		id2.setDireccion("X");
		id2.setPais("X");
		id2.setCodigoPostal(1);
		id2.setCiudad("X");
		id2.setNombre("X");
		id2.setApellido("Prueba2");
		id2.setCf(new ArrayList<>());
		em.persist(id2);

		// Divisas de ejemplo
		Divisa d1 = new Divisa();
		d1.setAbreviatura("EUR");
		d1.setCambioEuro(1.0);
		d1.setNombre("EURO");
		em.persist(d1);
		Divisa d2 = new Divisa();
		d2.setAbreviatura("USD");
		d2.setCambioEuro(1.6);
		d2.setNombre("US-DOLLAR");
		em.persist(d2);

		// CR de ejemplo
		Cuenta_Referencia cr = new Cuenta_Referencia();
		cr.setIBAN("IBANTESTCR1");
		cr.setDeps(new ArrayList<>());
		cr.setCobros(new ArrayList<>());
		cr.setPagos(new ArrayList<>());
		cr.setEstado("ALTA");
		cr.setSaldo(1.0);
		cr.setDiv(d1);
		cr.setNombreBanco("BANCOTEST");
		em.persist(cr);
		Cuenta_Referencia cr2 = new Cuenta_Referencia();
		cr2.setIBAN("IBANTESTCR2");
		cr2.setDeps(new ArrayList<>());
		cr2.setCobros(new ArrayList<>());
		cr2.setPagos(new ArrayList<>());
		cr2.setEstado("ALTA");
		cr2.setSaldo(0.0);
		cr2.setNombreBanco("BANCOTEST");
		cr2.setDiv(d2);
		em.persist(cr2);
		
		Cuenta_Referencia cr3 = new Cuenta_Referencia();
		cr3.setIBAN("IBANTESTCR3");
		cr3.setDeps(new ArrayList<>());
		cr3.setCobros(new ArrayList<>());
		cr3.setPagos(new ArrayList<>());
		cr3.setEstado("ALTA");
		cr3.setSaldo(0.0);
		cr3.setNombreBanco("BANCOTEST");
		cr3.setDiv(d1);
		em.persist(cr3);

		// Segregada de ejemplo
		Segregada se = new Segregada();
		se.setIBAN("IBANTESTSE1");
		se.setCobros(new ArrayList<>());
		se.setPagos(new ArrayList<>());
		se.setCr(cr);
		se.setCl(id2);
		se.setEstado("ALTA");
		se.setFecha_apertura(new Date());
		em.persist(se);
		

		// Empresa de ejemplo
		Empresa emp = new Empresa();
		emp.setId(1L);
		emp.setTipo_cliente("JUDIRICA");
		emp.setRazon_social("X");
		emp.setEstado("ALTA");
		emp.setFecha_alta(new Date());
		emp.setDireccion("X");
		emp.setPais("X");
		emp.setCodigoPostal(1);
		emp.setCiudad("X");
		emp.setAu(new ArrayList<>());
		emp.setCf(new ArrayList<>());
		em.persist(emp);

		Segregada se2 = new Segregada();
		se2.setIBAN("IBANTESTSE2");
		se2.setCobros(new ArrayList<>());
		se2.setPagos(new ArrayList<>());
		se2.setCr(cr2);
		se2.setCl(emp);
		se2.setEstado("ALTA");
		se2.setFecha_apertura(new Date());
		em.persist(se2);
		
		Segregada se3 = new Segregada();
		se3.setIBAN("IBANTESTSE3");
		se3.setCobros(new ArrayList<>());
		se3.setPagos(new ArrayList<>());
		se3.setCr(cr3);
		se3.setCl(id2);
		se3.setEstado("BAJA");
		se3.setFecha_apertura(new Date());
		em.persist(se3);


		// AU de ejemplo
		Autorizacion au = new Autorizacion();
		Autorizacion_PK auPK = new Autorizacion_PK();
		auPK.setEmID(emp.getId());
		auPK.setPaID(pa2.getID());
		au.setId(auPK);
		au.setTipo("tipo");
		au.setEm(emp);
		au.setPa(pa2);
		em.persist(au);
		emp.getAu().add(au);
		
		Autorizacion au2 = new Autorizacion();
		Autorizacion_PK auPK2 = new Autorizacion_PK();
		auPK2.setEmID(emp.getId());
		auPK2.setPaID(pa.getID());
		au2.setId(auPK2);
		au2.setEm(emp);
		au2.setTipo("tipo");
		au2.setPa(pa);
		em.persist(au2);
		emp.getAu().add(au2);
		
		em.getTransaction().commit();

		em.close();
		emf.close();
	}

	public static String hashPassword(String pw, String s) {
		ByteArrayOutputStream contra = new ByteArrayOutputStream();
		try {
			contra.write(pw.getBytes());
			contra.write(s.getBytes());
			MessageDigest mg = MessageDigest.getInstance("SHA-256");

			return new String(mg.digest(contra.toByteArray()));

		} catch (Exception e) {

		}
		return null;
	}
}
