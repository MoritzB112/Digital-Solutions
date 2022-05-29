package es.uma.proyecto.ejb;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
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

@Singleton
@Startup
public class InicializaBBDD {

	private static SecureRandom sm = new SecureRandom();

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;

	@PostConstruct
	public void inicializar() {

		if (em.find(Usuario.class, "juan") != null) {
			return;
		}


		
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
		
		Individual ind = new Individual(); //2L
		ind.setIdentificacion("63937528N");
		ind.setNombre("Juan");
		ind.setApellido("Pérez");
		ind.setUs(a);
		ind.setCiudad("Sevilla");
		ind.setDireccion("Casa Moritz");
		ind.setPais("Uzbekistan");
		ind.setCodigoPostal(1234);
		ind.setFecha_nacimiento(new Date());
		ind.setEstado("ALTA");
		ind.setFecha_alta(new Date());
		ind.setTipo_cliente("FISICA");
		em.persist(ind);
		
		Empresa emp = new Empresa(); //1L
		emp.setIdentificacion("P3310693A");
		emp.setTipo_cliente("JURIDICA");
		emp.setEstado("ALTA");
		emp.setFecha_alta(new Date());
		emp.setDireccion("Avenida del Cid");
		emp.setCiudad("Granada");
		emp.setCodigoPostal(29000);
		emp.setPais("España");
		emp.setRazon_social("TECHFUTURE");
		em.persist(emp);
		
		Persona_Autorizada pa = new Persona_Autorizada(); //3L
		pa.setIdentificacion("Y4001267V");
		pa.setApellidos("García");
		pa.setNombre("Andrea");
		pa.setDireccion("Plaza Alta");
		pa.setCiudad("Madrid");
		pa.setCodigoPostal(21000);
		pa.setPais("España");
		pa.setEstado("ALTA");
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
		em.persist(au);
		
		
		Divisa divDol = new Divisa();
		divDol.setNombre("Dolares");
		divDol.setAbreviatura("USD");
		divDol.setCambioEuro(2.0);
		divDol.setSimbolo("$");
		em.persist(divDol);
		
		
		Divisa divEur = new Divisa();
		divEur.setNombre("Euros");
		divEur.setAbreviatura("Eur");
		divEur.setCambioEuro(1.0);
		divEur.setSimbolo("€");
		em.persist(divEur);
		
		Divisa divLib = new Divisa();
		divLib.setNombre("Libras");
		divLib.setAbreviatura("Lb");
		divLib.setCambioEuro(0.75);
		divLib.setSimbolo("£");
		em.persist(divLib);
		
		Cuenta_Referencia cr = new Cuenta_Referencia();
		cr.setIBAN("VG57DDVS5173214964983931");
		cr.setDiv(divEur);
		cr.setEstado("ALTA");
		cr.setNombreBanco("SANTANDER");
		cr.setSaldo(24351.15);
		em.persist(cr);
		
		Cuenta_Referencia cr2 = new Cuenta_Referencia();
		cr2.setIBAN("HN47QUXH11325678769785549996");
		cr2.setDiv(divEur);
		cr2.setEstado("ALTA");
		cr2.setNombreBanco("UNICAJA");
		cr2.setSaldo(54671.15);
		em.persist(cr2);
		
		Segregada se = new Segregada();
		se.setIBAN("NL63ABNA6548268733");
		se.setCl(emp);
		se.setEstado("ALTA");
		se.setFecha_apertura(new Date());
		se.setCr(cr);
		em.persist(se);
		
		
		Segregada se2 = new Segregada();
		se2.setIBAN("FR5514508000502273293129K55");
		se2.setEstado("ALTA");
		se2.setCl(emp);
		se2.setFecha_apertura(new Date());
		se2.setCr(cr2);
		em.persist(se2);
		
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, 9);
		cal.set(Calendar.YEAR, 2021);
		
		
		
		Cuenta_Referencia cr6 = new Cuenta_Referencia();
		cr6.setIBAN("ESPRUEBA12312341095725");
		cr6.setNombreBanco("UNICAJAV2");
		cr6.setSaldo(100.0);
		cr6.setEstado("ALTA");
		cr6.setDiv(divEur);
		em.persist(cr6);
		
		Segregada se3 = new Segregada();
		se3.setIBAN("DE31500105179261215675");
		se3.setCr(cr6);
		se3.setCl(emp);
		se3.setEstado("BAJA");
		se3.setFecha_apertura(new Date());
		se3.setFecha_cierre(cal.getTime());
		em.persist(se3);
		
		Cuenta_Referencia cr3 = new Cuenta_Referencia();
		cr3.setIBAN("ES7121007487367264321882");
		cr3.setNombreBanco("CAJAMAR");
		cr3.setSaldo(100.0);
		cr3.setEstado("ALTA");
		cr3.setDiv(divEur);
		em.persist(cr3);
		
		Cuenta_Referencia cr4 = new Cuenta_Referencia();
		cr4.setIBAN("VG88HBIJ4257959912673134");
		cr4.setNombreBanco("SANTANDER");
		cr4.setSaldo(200.0);
		cr4.setEstado("ALTA");
		cr4.setDiv(divDol);
		em.persist(cr4);
		
		Cuenta_Referencia cr5 = new Cuenta_Referencia();
		cr5.setIBAN("GB79BARC20040134265953");
		cr5.setNombreBanco("LA CAIXA");
		cr5.setSaldo(134.0);
		cr5.setEstado("ALTA");
		cr5.setDiv(divLib);
		em.persist(cr5);
				
		Pooled_Account pooA = new Pooled_Account();
		pooA.setIBAN("ES8400817251647192321264");
		pooA.setEstado("ALTA");
		pooA.setFecha_apertura(new Date());
		pooA.setCl(ind);
		em.persist(pooA);
	
		Depositado_en dep1 = new Depositado_en();
		dep1.setSaldo(781.32);
		dep1.setCr(cr3);
		dep1.setPa(pooA);
		
		
		Depositado_en_PK dep_PK1 = new Depositado_en_PK();
		dep_PK1.setCrID(cr3.getIBAN());
		dep_PK1.setPaID(pooA.getIBAN());
		dep1.setId(dep_PK1);
		em.persist(dep1);
		
		Depositado_en dep2 = new Depositado_en();
		dep2.setSaldo(541.32);
		dep2.setCr(cr4);
		dep2.setPa(pooA);
		
		
		Depositado_en_PK dep_PK2 = new Depositado_en_PK();
		dep_PK2.setCrID(cr4.getIBAN());
		dep_PK2.setPaID(pooA.getIBAN());
		dep2.setId(dep_PK2);
		em.persist(dep2);
		
		Depositado_en dep3 = new Depositado_en();
		dep3.setSaldo(1132.32);
		dep3.setCr(cr5);
		dep3.setPa(pooA);
		
		
		Depositado_en_PK dep_PK3 = new Depositado_en_PK();
		dep_PK3.setCrID(cr5.getIBAN());
		dep_PK3.setPaID(pooA.getIBAN());
		dep3.setId(dep_PK3);
		em.persist(dep3);
		
		
		Transaccion trans = new Transaccion(); //10L
		trans.setDivEm(divDol);
		trans.setDivRec(divDol);
		trans.setCantidad(200.0);
		trans.setOrigen(se);
		trans.setDestino(pooA);
		trans.setTipo("pago");
		trans.setTipo("DEBITO");
		trans.setFechaInstruccion(new Date());
		em.persist(trans);
		
		Usuario admin = new Usuario();
		admin.setUsuario("ponciano");
		admin.setSalt(parseString(getSalt()));
		admin.setPassword(parseString(hashPassword(parseByte(admin.getSalt()), "ponciano".getBytes())));
		admin.setEsAdministrativo(true);
		em.persist(admin);
		
		Individual iTest=new Individual();
		iTest.setIdentificacion("TESTDNI");
		iTest.setTipo_cliente("FISICA");
		iTest.setEstado("ALTA");
		iTest.setFecha_alta(new Date());
		iTest.setDireccion("t");
		iTest.setCiudad("t");
		iTest.setCodigoPostal(2);
		iTest.setPais("t");
		iTest.setNombre("nombreTest");
		iTest.setApellido("apellidoTest");
		em.persist(iTest);
		
		Pooled_Account paTest=new Pooled_Account();
		paTest.setIBAN("TESTPADEL");
		paTest.setEstado("ALTA");
		paTest.setFecha_apertura(new Date());
		paTest.setCl(emp);
		em.persist(paTest);
		
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
