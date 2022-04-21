package es.uma.informatica.sii.ejb.practica;

import java.util.logging.Logger;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.proyecto.GestionDivisas;

public class Trazabilidad {
	
	private static final Logger LOG = Logger.getLogger(Trazabilidad.class.getCanonicalName());

	private static final String LOTES_EJB = "java:global/classes/DivisasEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejb_TESTS";
	
	private GestionDivisas gestionDivisas;
	
	
	
	@Before
	public void setup() throws NamingException  {
<<<<<<< HEAD
		
=======
//		gestionDivisas = (GestionDivisas) SuiteTest.ctx.lookup(LOTES_EJB);
>>>>>>> 3f18f9cde43b230dc7d17b2850e26a9a9cf74ddf
//		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}

	@Test
<<<<<<< HEAD
	public void testTest() {
			
=======
	public void testTest() {	
>>>>>>> 3f18f9cde43b230dc7d17b2850e26a9a9cf74ddf
		
	}

}
