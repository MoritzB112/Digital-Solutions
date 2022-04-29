package es.uma.informatica.sii.ejb.practica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.proyecto.Autorizacion;
import es.uma.proyecto.Autorizacion_PK;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.GestionClientes;
import es.uma.proyecto.GestionPersonas_Autorizadas;
import es.uma.proyecto.GestionUsuarios;
import es.uma.proyecto.Persona_Autorizada;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.Excepciones.AutorizacionYaExisteException;
import es.uma.proyecto.Excepciones.Persona_AutorizadaNoEncontradaException;
import es.uma.proyecto.Excepciones.Persona_AutorizadaYaExisteException;
import es.uma.proyecto.Excepciones.UsuarioNoEncontradoException;

public class PruebaPersonaAutorizada {
	
	private static final String PERSONA_ATORIZADA_EJB = "java:global/classes/Personas_AutorizadasEJB";
	private static final String USUARIOS_EJB = "java:global/classes/UsuariosEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejb_TESTS";
	private static final String CLIENTE_EJB = "java:global/classes/ClientesEJB";
	
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

	@Test //Da de alta a una nueva Persona_Autorizada
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
	
	@Test //Da de alta a una nueva Persona_Autorizada ya existente y falla
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
			Usuario u=new Usuario();
			u.setUsuario("No existe");
		gestionPA.insertarPersonaAutorizada(u,paut);
		fail("Se debería haber lanzado la excepción UsuarioNoEncontradoException");
		}catch(UsuarioNoEncontradoException e) {
			
		}catch(Exception e) {
			fail("Se debería haber lanzado la excepción UsuarioNoEncontradoException");
		}
	}
	
	@Test //Da de alta a una nueva Persona_Autorizada
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
			fail("Se debería haber lanzado la excepción Persona_AutorizadaYaExisteException");
		}			
}
	
	@Test //Da de alta a una nueva Persona_Autorizada
	public void darAutorizacionExistenteTest() {
		Autorizacion au=new Autorizacion();
		
		au.setTipo("test");
		
		try {

			Empresa ent=new Empresa();
			ent.setId(2L);
			Persona_Autorizada pa=new Persona_Autorizada();
			pa.setID(2L);
			gestionPA.darAutorizacion(ent, au, pa);
			fail("Se debería haber lanzado la excepción AutorizacionYaExisteException");
			
			
		}catch(AutorizacionYaExisteException e) {
			
		}catch(Exception e) {
			fail("Se debería haber lanzado la excepción AutorizacionYaExisteException");
		}
	}
	
	@Test
	@Requisitos({"RF6"}) //Da una autorisacion de una empresa a una Persona Autorizada
	public void darAutorizacionTest() {
		Autorizacion au=new Autorizacion();
		
		au.setTipo("test");
		
		try {
			
			
			Empresa ent=new Empresa();
			ent.setId(1L);
			Persona_Autorizada pa=new Persona_Autorizada();
			pa.setID(1L);
			gestionPA.darAutorizacion(ent, au, pa);
			assertTrue(gestionPA.sacarAutorizaciones().contains(au));
			assertEquals(au.getEm(),ent);
			assertEquals(au.getPa(),pa);
			
		}catch(Exception e) {
			fail("No se debe lanzar excepción");
		}
	}
	
	@Test
	@Requisitos({"RF8"}) //Da de baja una presona autorizada
	public void eliminarAutorizadoCuentaTest() {
		Persona_Autorizada paut= new Persona_Autorizada();
		paut.setID(1L);
		Persona_Autorizada paut2=null;
		
		try {
			gestionPA.eliminarAutorizadoCuenta(paut);
			for(Persona_Autorizada a:gestionPA.sacarPA()) {
				if(a.getID().equals(paut.getID())) {
					paut2=a;
				}
			}
			assertEquals("BAJA", paut2.getEstado().toUpperCase());
			
		}catch(Exception e) {
			fail("No se debe lanzar excepción");
		}
	}
	
	@Test
	@Requisitos({"RF16"}) //Bloquea una presona autorizada
	public void bloquearAutorizadoTest() {
		Persona_Autorizada paut= new Persona_Autorizada();
		paut.setID(1L);
		Persona_Autorizada paut2=null;
		
		try {
			gestionPA.bloquearAutorizado(paut);
			for(Persona_Autorizada a:gestionPA.sacarPA()) {
				if(a.getID().equals(paut.getID())) {
					paut2=a;
				}
			}
			assertEquals("BLOQUEADO", paut2.getEstado().toUpperCase());
			
		}catch(Exception e) {
			fail("No se debe lanzar excepción");
		}
	}
	
	@Test
	@Requisitos({"RF16"}) //Desbloquea una presona autorizada
	public void desbloquearAutorizadoTest() {
		Persona_Autorizada paut= new Persona_Autorizada();
		paut.setID(1L);
		Persona_Autorizada paut2=null;
		
		try {
			gestionPA.bloquearAutorizado(paut);
			gestionPA.desbloquearAutorizado(paut);
			for(Persona_Autorizada a:gestionPA.sacarPA()) {
				if(a.getID().equals(paut.getID())) {
					paut2=a;
				}
			}
			assertEquals("ALTA", paut2.getEstado().toUpperCase());
			
		}catch(Exception e) {
			fail("No se debe lanzar excepción");
		}
	}
	
	@Test
	@Requisitos({"RF7"}) //Modifica los datod de una presona autorizada existente
	public void modificarDatosAutorizadoTest() {
		Persona_Autorizada paut= gestionPA.sacarPA().get(0);
		Persona_Autorizada paut2=null;
		paut.setCiudad("Florencia");
		paut.setApellidos("da Vinci");
		paut.setNombre("Leonardo");
		
		
		try {
			gestionPA.modificarDatosAutorizado(paut);
			for(Persona_Autorizada a:gestionPA.sacarPA()) {
				if(a.getID().equals(paut.getID())) {
					paut2=a;
				}
			}
			assertEquals(paut.getCiudad(), paut2.getCiudad());
			assertEquals(paut.getApellidos(), paut2.getApellidos());
			assertEquals(paut.getNombre(), paut2.getNombre());
			
		}catch(Exception e) {
			fail("No se debe lanzar excepción");
		}
		
	}
	
	@Test
	@Requisitos({"RF7"}) //Modifica los datod de una presona autorizada no existente, resulta en error
	public void modificarDatosAutorizadoNoExistenteTest() {
		Persona_Autorizada paut= new Persona_Autorizada();
		paut.setID(10L);
		paut.setCiudad("Florencia");
		paut.setApellidos("da Vinci");
		paut.setNombre("Leonardo");
		
		
		try {
			gestionPA.modificarDatosAutorizado(paut);
			fail("Se debería haber lanzado la excepción Persona_AutorizadaNoEncontradaException");
			
		}catch(Persona_AutorizadaNoEncontradaException e) {
			
		}catch(Exception e){
			fail("Se debería haber lanzado la excepción Persona_AutorizadaNoEncontradaException");
		}
		
	}
	
	@Test
	@Requisitos({"RF11"}) //Saca las personas autorizadas para poder generar reportes
	public void sacarPATest() {
		assertEquals(2, gestionPA.sacarPA().size());
	}
	
	@Test
	@Requisitos({"RF11"}) //Saca las autorizaciones para poder generar reportes
	public void sacarAutorizacionesTest() {
		assertEquals(1, gestionPA.sacarAutorizaciones().size());
	}
}
