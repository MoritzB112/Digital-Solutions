package es.uma.informatica.sii.ejb.practica;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.proyecto.Depositado_en;
import es.uma.proyecto.Depositado_en_PK;
import es.uma.proyecto.GestionTransacciones;
import es.uma.proyecto.Transaccion;
import es.uma.proyecto.Excepciones.DepositoNoExisteException;

public class PruebaTransacciones {
	
	private static final String TRANSACCIONES_EJB = "java:global/classes/TransaccionesEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejb_TESTS";
	
	private GestionTransacciones gestionTransacciones;
	
	
	
	@Before
	public void setup() throws NamingException  {
		gestionTransacciones = (GestionTransacciones) SuiteTest.ctx.lookup(TRANSACCIONES_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	@Test
	@Requisitos({"RF17", "RF18"}) //Cambia la divisa de dos Depositados_En en la misma Pooled Account
	public void cambioDivisaTest() {
		Transaccion t=new Transaccion();
		t.setID_unico(10L);
		t.setCantidad(1.0);
		t.setComision(0.0);
		Long l=System.currentTimeMillis();
		t.setFechaInstruccion(new Date(l));
		
		Depositado_en de1=new Depositado_en();
		Depositado_en_PK de1PK=new Depositado_en_PK();
		de1PK.setCrID("IBANTESTCR1");
		de1PK.setPaID("IBANTESTPA1");
		de1.setId(de1PK);
		Depositado_en de2=new Depositado_en();
		Depositado_en_PK de2PK=new Depositado_en_PK();
		de2PK.setCrID("IBANTESTCR2");
		de2PK.setPaID("IBANTESTPA1");
		de2.setId(de2PK);
		try {
			gestionTransacciones.cambioDivisa(t, de1, de2);
			assertTrue(gestionTransacciones.sacarTransacciones().contains(t));
		}catch (Exception e) {
			fail("No se deberia de haber lanzado una excepcion");
		}
	}
	
	@Test
	@Requisitos({"RF17", "RF18"}) //Cambia la divisa de dos Depositados_En pero uno no existe y falla
	public void cambioDivisaNoOrigenTest() {
		Transaccion t=new Transaccion();
		t.setID_unico(10L);
		t.setCantidad(1.0);
		t.setComision(0.0);
		Long l=System.currentTimeMillis();
		t.setFechaInstruccion(new Date(l));
		
		Depositado_en de1=new Depositado_en();
		Depositado_en_PK de1PK=new Depositado_en_PK();
		de1PK.setCrID("NOEXISTE");
		de1PK.setPaID("IBANTESTPA1");
		de1.setId(de1PK);
		Depositado_en de2=new Depositado_en();
		Depositado_en_PK de2PK=new Depositado_en_PK();
		de2PK.setCrID("IBANTESTCR2");
		de2PK.setPaID("IBANTESTPA1");
		de2.setId(de2PK);
		try {
			gestionTransacciones.cambioDivisa(t, de1, de2);
			fail("Se deberia haber lanzado excepcion");
		}catch (DepositoNoExisteException e) {
			
		}catch(Exception e){
			fail("No se deberia de haber lanzado una excepcion");
		}
	}
	
	@Test
	public void sacarTransaccionesTest() {
		assertEquals(1,gestionTransacciones.sacarTransacciones().size());
	}
}
