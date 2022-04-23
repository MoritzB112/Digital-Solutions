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
		gestionDivisas = (GestionDivisas) SuiteTest.ctx.lookup(LOTES_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}

	@Test
	public void testTest() {
		gestionDivisas.actualizarDivisas();
	}

}
