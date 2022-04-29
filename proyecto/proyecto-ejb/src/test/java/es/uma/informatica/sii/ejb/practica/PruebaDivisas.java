package es.uma.informatica.sii.ejb.practica;

import es.uma.proyecto.Divisa;
import es.uma.proyecto.GestionDivisas;
import es.uma.proyecto.Excepciones.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.logging.Logger;

import javax.naming.NamingException;
import org.junit.Before;
import org.junit.Test;


public class PruebaDivisas {
	
	private static final Logger LOG = Logger.getLogger(PruebaDivisas.class.getCanonicalName());

	private static final String LOTES_EJB = "java:global/classes/DivisasEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejb_TESTS";
	
	private GestionDivisas gestionDivisas;
	
	
	@Before
	public void setup() throws NamingException  {
		gestionDivisas = (GestionDivisas) SuiteTest.ctx.lookup(LOTES_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);

	}

	
	@Test //Permite meter una nueva divisa en la BBDD
	public void insertarDivisaTest() {
		int preSize = gestionDivisas.getDivisas().size();
		
		Divisa d = new Divisa();
		d.setAbreviatura("BTC");
		d.setCambioEuro(50000.0);
		d.setNombre("BITCOIN");
		
		try {		
			gestionDivisas.insertarDivisa(d);		
		} catch (DivisaYaExisteException e) {
			fail("TEST ERROR: No deberia lanzar excepcion.");
		}
		
		List<Divisa> divisas = gestionDivisas.getDivisas();
		assertTrue(divisas.contains(d));
		assertEquals(divisas.size(), preSize+1);
	}
	
	@Test //Permite modificar una divisa existente en la BBDD
	public void modificarDivisaExistenteTest() {
		Divisa d1 = gestionDivisas.getDivisas().get(0);
		d1.setCambioEuro(0.5);
		Divisa d2 = null;
		
		try {
			gestionDivisas.modificarDivisa(d1);
			for (Divisa x : gestionDivisas.getDivisas()) {
				if(x.getAbreviatura().equals(d1.getAbreviatura())) {
					d2 = x;
				}
			}
			assertEquals(d1.getCambioEuro(), d2.getCambioEuro());
		} catch (Exception e) {
			fail("TEST ERROR: No deberia lanzar excepcion.");
		}
	}
	
	@Test //Permite modificar una divisa no existente y da error
	public void modificarDivisaNoExistenteTest() {
		Divisa d = new Divisa();
		d.setAbreviatura("NA");
		d.setCambioEuro(50.0);
		d.setNombre("NOEXISTE");
		
		try {
			gestionDivisas.modificarDivisa(d);
			fail("No deberia continuar por aqui...");
		} catch(DivisaNoExisteException e) {
			//OK
		} catch (Exception e) {
			fail("No deberia continuar por aqui...");
		}
	}
	
	@Test //Sacara todas las divisas de la BBDD
	public void getDivisasTest() {
		assertEquals(2, gestionDivisas.getDivisas().size());
	}
	
}
