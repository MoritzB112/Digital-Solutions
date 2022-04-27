package es.uma.informatica.sii.ejb.practica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.proyecto.Autorizacion;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.GestionClientes;
import es.uma.proyecto.GestionPersonas_Autorizadas;
import es.uma.proyecto.GestionUsuarios;
import es.uma.proyecto.Persona_Autorizada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.Excepciones.Persona_AutorizadaYaExisteException;
import es.uma.proyecto.Excepciones.UsuarioNoEncontradoException;

public class PersonaAutorizadaTest {
	
	private static final String PERSONA_ATORIZADA_EJB = "java:global/classes/Persona_AutorizadasEJB";
	private static final String USUARIOS_EJB = "java:global/classes/UsuariosEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejb_TESTS";
	private static final String CLIENTE_EJB = "java:global/classes/ClienteEJB";
	
	private GestionPersonas_Autorizadas gestionPA;
	
	private GestionUsuarios gesUsuarios;
	
	private GestionClientes gesClientes;
	
	
	
	@Before
	public void setup() throws NamingException  {
		gestionPA = (GestionPersonas_Autorizadas) SuiteTest.ctx.lookup(PERSONA_ATORIZADA_EJB);
		gesUsuarios = (GestionUsuarios) SuiteTest.ctx.lookup(USUARIOS_EJB);
		gesClientes = (GestionClientes) SuiteTest.ctx.lookup(CLIENTE_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}

	@Test
	public void insertarPersonaAutorizadaTest() {	
	
		Persona_Autorizada paut= new Persona_Autorizada();
		paut.setNombre("Alberto");
		paut.setApellidos("García García");
		paut.setDireccion("Calle Larios, 1");
		paut.setCiudad("Málaga");
		paut.setCodigoPostal(27030);
		paut.setPais("España");
		paut.setID(10L);
		
		try {
			Usuario u=new Usuario();
			u.setUsuario("testPA1");
		gestionPA.insertarPersonaAutorizada(u,paut);
		assertTrue(gestionPA.sacarPA().contains(paut));
		}catch(Exception e) {
			fail("No se debe lanzar excepción");
		}
			
}
	
	@Test
	public void insertarPersonaAutorizadaNoUsuarioTest() {	
	
		Persona_Autorizada paut= new Persona_Autorizada();
		paut.setNombre("Alberto");
		paut.setApellidos("García García");
		paut.setDireccion("Calle Larios, 1");
		paut.setCiudad("Málaga");
		paut.setCodigoPostal(27030);
		paut.setPais("España");
		paut.setID(10L);
		
		try {
		gestionPA.insertarPersonaAutorizada(new Usuario(),paut);
		fail("Se debería haber lanzado la excepción UsuarioNoEncontradoException");
		}catch(UsuarioNoEncontradoException e) {
			
		}catch(Exception e) {
			fail("No se debe lanzar esta excepción");
		}
	}
	
	@Test
	public void insertarPersonaAutorizadaExistenteTest() {	
	
		Persona_Autorizada paut= new Persona_Autorizada();
		paut.setID(1L);
		
		try {
			Usuario u=new Usuario();
			u.setUsuario("testPA1");
		gestionPA.insertarPersonaAutorizada(u,paut);
		fail("Se debería haber lanzado la excepción Persona_AutorizadaYaExisteException");
		}catch(Persona_AutorizadaYaExisteException e) {
			
		}catch(Exception e) {
			fail("No se debe lanzar esta excepción");
		}			
}
	
	@Test
	//Terminar
	public void darAutorizacionTest() {
		Autorizacion au=new Autorizacion();
		
		au.setTipo("test");
		
		try {
			
			
			Empresa ent=gesClientes.sacarEmpresas().get(0);
			Persona_Autorizada pa=gestionPA.sacarPA().get(0);
			gestionPA.darAutorizacion(ent, au, pa);
			assertTrue(gestionPA.sacarAutorizaciones().contains(au));
			assertEquals(au.getEm(),ent);
			assertEquals(au.getPa(),pa);
			
		}catch(Exception e) {
			fail("No se debe lanzar excepción");
		}
	}
}
