package es.uma.informatica.sii.ejb.practica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.Cuenta;
import es.uma.proyecto.Cuenta_Referencia;
import es.uma.proyecto.Depositado_en;
import es.uma.proyecto.Depositado_en_PK;
import es.uma.proyecto.Divisa;
import es.uma.proyecto.GestionCuentas;
import es.uma.proyecto.Pooled_Account;
import es.uma.proyecto.Segregada;
import es.uma.proyecto.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.Excepciones.CuentaNoSuporteadaException;
import es.uma.proyecto.Excepciones.DivisaNoExisteException;
import es.uma.proyecto.Excepciones.SaldoNoVacioException;

public class PruebaCuentas {
	
	private static final String CUENTAS_EJB = "java:global/classes/CuentasEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejb_TESTS";
	private GestionCuentas gestionCuentas;
	
	@Before
	public void setup() throws NamingException  {
		gestionCuentas = (GestionCuentas) SuiteTest.ctx.lookup(CUENTAS_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}

	@Test
	@Requisitos({"RF5"}) //Permite crear una cuenta Pooled y asociarlo a un cliente existente
	public void addCuentaPooledTest() {
		Pooled_Account pa = new Pooled_Account();
		Cuenta_Referencia ref = new Cuenta_Referencia();
		Cliente cl = new Cliente();
		cl.setId(1L);
		ref.setIBAN("IBANTESTCR2");
		pa.setIBAN("AS1100");
		pa.setFecha_apertura(new Date());
		pa.setEstado("activo");
		
		try {
			gestionCuentas.addCuenta(pa, ref, cl);
			assertTrue(gestionCuentas.sacarPooledAccount().contains(pa));
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}
		
	}
	
	@Test
	@Requisitos({"RF5"}) //Permite crear una cuenta Segregada y asociarlo a un cliente existente
	public void addCuentaSegTest() {
		Segregada se = new Segregada();
		Cuenta_Referencia ref = new Cuenta_Referencia();
		Cliente cl = new Cliente();
		cl.setId(1L);
		ref.setIBAN("IBANTESTCR2");
		se.setIBAN("AS1100");
		se.setFecha_apertura(new Date());
		se.setEstado("activo");
		
		try {
			gestionCuentas.addCuenta(se, ref, cl);
			assertTrue(gestionCuentas.sacarSegregadas().contains(se));
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}
		
	}
	
	
	@Test
	@Requisitos({"RF5"}) //Es para dar de alta una cuenta de referencia(externa), con una divisa
	public void abrirCuentaRefTest() {
		Cuenta_Referencia cf = new Cuenta_Referencia();
		Divisa dv = new Divisa();
		dv.setAbreviatura("EUR");
		
		cf.setNombreBanco("SANTANDER");
		cf.setSaldo(1500.88);
		cf.setIBAN("AS2000");
		
		try {
			gestionCuentas.abrirCuentaReferencia(cf, dv);
			assertTrue(gestionCuentas.sacarCuentaReferencia().contains(cf));
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}
		
	}
	
	@Test
	@Requisitos({"RF5"}) //Es para dar de alta una cuenta de referencia(externa), pero la divisa no existe (falla)
	public void abrirCuentaRefNoDivisaTest() {
		Cuenta_Referencia cf = new Cuenta_Referencia();
		Divisa dv = new Divisa();
		dv.setAbreviatura("POL");
		
		cf.setNombreBanco("SANTANDER");
		cf.setSaldo(1500.88);
		cf.setIBAN("AS2000");
		
		try {
			gestionCuentas.abrirCuentaReferencia(cf, dv);
			fail("No debería haberse creado una cuenta");
		}catch (DivisaNoExisteException e) {
			
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}
		
	}
	
	@Test
	@Requisitos({"RF9"}) //Permite cerrar una cuenta con saldo 0.0 cambia al estado BAJA
	public void cerrarCuentaTest() {
		Segregada se = new Segregada();
		se.setIBAN("IBANTESTSE2");
		
		try {
			gestionCuentas.cerrarCuenta(se);
			Segregada se2 = null;
			for(Segregada x : gestionCuentas.sacarSegregadas()){
				if(x.getIBAN().equals(se.getIBAN())) {
					se2 = x;
				}
			}
			assertEquals("BAJA", se2.getEstado().toUpperCase());
		} catch (Exception e) {
			fail("No debería haber dado excepción");
		}
	}
	
	@Test
	@Requisitos({"RF9"}) //Permite cerrar una cuenta con saldo > 0.0, fallara
	public void cerrarCuentaPoolVacioTest() {
		Pooled_Account pa = new Pooled_Account();
		pa.setIBAN("IBANTESTPA1");
		
		try {
			gestionCuentas.cerrarCuenta(pa);
			fail("No debería haberse cerrado");
		}catch (SaldoNoVacioException e) {
			
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}
	}
	
	@Test
	@Requisitos({"RF11"}) //Seca toda la informacion de una cuenta
	public void sacarCuentaTest() {
		Pooled_Account pa = new Pooled_Account();
		pa.setIBAN("IBANTESTPA1");

		try {
			assertEquals(pa, gestionCuentas.sacarCuenta(pa));
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}
		
	}
	
	@Test
	@Requisitos({"RF11"}) //Seca toda la informacion de una cuenta, pero no le pasa una cuenta de tipo segregada ni pooled y fallara
	public void sacarCuentaNoSuporTest() {
		Cuenta cu = new Cuenta();
		cu.setIBAN("ES123");
		
		try {
			assertEquals(cu, gestionCuentas.sacarCuenta(cu));
			fail("No debería haberse cerrado");
			
		}catch (CuentaNoSuporteadaException e) {
			
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}
	}
	
	@Test
	public void addCuentaPoolaCarteraTest() {
		Cuenta_Referencia ref = new Cuenta_Referencia();
		ref.setIBAN("IBANTESTCR3");
		Pooled_Account pa = new Pooled_Account();
		pa.setIBAN("IBANTESTPA1");
		
		try {
			gestionCuentas.addCuenta(pa, ref);
			Depositado_en_PK dep = new Depositado_en_PK();
			dep.setCrID(ref.getIBAN());
			dep.setPaID(pa.getIBAN());
			Depositado_en dp = new Depositado_en();
			dp.setId(dep);
			Pooled_Account pa2 = null;
			for(Pooled_Account x : gestionCuentas.sacarPooledAccount()){
				if(x.getIBAN().equals(pa.getIBAN())) {
					pa2 = x;
				}
			}
			assertTrue(pa2.getDeps().contains(dp));
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}
	}
	
	@Test
	public void addCuentaPoolaCarteraNoExistTest() {
		Cuenta_Referencia ref = new Cuenta_Referencia();
		ref.setIBAN("IBANTESTCR3");
		Pooled_Account pa = new Pooled_Account();
		pa.setIBAN("IBANTESTPA500");
		
		try {
			gestionCuentas.addCuenta(pa, ref);
			fail("No debería haberse ejecutado");
		}catch (CuentaNoExisteException e) {
			assertEquals("POOLED_ACCOUNT", e.getMessage());
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}
	}
	
	@Test
	@Requisitos({"RF10"}) //Seca todas las transacciones asociados a un cliente
	public void sacarTrasaccionesTest() {
		Pooled_Account pa = new Pooled_Account();
		pa.setIBAN("IBANTESTPA1");
		Segregada se = new Segregada();
		se.setIBAN("IBANTESTSE1");
		
		try {
			assertEquals(2, gestionCuentas.sacarTransacciones(pa).size());
			assertEquals(0, gestionCuentas.sacarTransacciones(se).size());
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}
	}
	
	@Test
	@Requisitos({"RF11"}) //Seca toda la informacion de una cuenta para el reporte por ejemplo
	public void sacarPooledAccountTest() {
		assertEquals(1, gestionCuentas.sacarPooledAccount().size());
	}
	
	@Test
	@Requisitos({"RF11", "RF12"}) //Seca toda la informacion de una cuenta para el reporte por ejemplo
	public void sacarSegregadasTest() {
		assertEquals(2, gestionCuentas.sacarSegregadas().size());
	}
	
	@Test
	@Requisitos({"RF11"}) //Seca toda la informacion de una cuenta para el reporte por ejemplo
	public void sacarReferenciasTest() {
		assertEquals(3, gestionCuentas.sacarCuentaReferencia().size());
	}
	
}
