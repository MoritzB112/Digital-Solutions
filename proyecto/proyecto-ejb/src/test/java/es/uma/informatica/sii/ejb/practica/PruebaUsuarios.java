package es.uma.informatica.sii.ejb.practica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.proyecto.ejb.GestionUsuarios;
import es.uma.proyecto.ejb.Excepciones.ContraseñaIncorrectaException;
import es.uma.proyecto.ejb.Excepciones.UsuarioExistenteException;
import es.uma.proyecto.ejb.Excepciones.UsuarioNoEncontradoException;
import es.uma.proyecto.jpa.Usuario;

public class PruebaUsuarios {

	private static final String USUARIOS_EJB = "java:global/classes/UsuariosEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejb_TESTS";
	private GestionUsuarios gestionUsuarios;
	
	@Before
	public void setup() throws NamingException  {
		gestionUsuarios = (GestionUsuarios) SuiteTest.ctx.lookup(USUARIOS_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}

	
	@Test
	@Requisitos({"RF2"}) // Hay que crear un usuario antes que puedes dar de alta a un cliente
	public void crearUsuarioTest() {
		Usuario u = new Usuario();
		u.setUsuario("prueba1");
		u.setEsAdministrativo(false);
		u.setPassword("umaFeliz");
		
		try {
			gestionUsuarios.crearUsuario(u);
			assertTrue(gestionUsuarios.sacarUsuarios().contains(u));
		}catch(Exception e) {
			fail("No debería haber dado excepción");
		}
	}
	
	@Test
	@Requisitos({"RF2"}) //El usuario ya existe y falla
	public void CrearExistente() {
		Usuario u = new Usuario();
		u.setUsuario("testPA1");
		u.setEsAdministrativo(false);
		u.setPassword("cuidadito");
		
		try {
			gestionUsuarios.crearUsuario(u);
			fail("Debería haber dado una excepción");
		}catch (UsuarioExistenteException e) {
			
		}catch (Exception e) {
			fail("No se deberia de haber lanzado una excepcion");
		}
	}
	
	@Test
	@Requisitos({"RF1"}) //Comprueba si un Usuario es administrativo o no
	public void comprobarAdministrativoExistente() {
		Usuario u = new Usuario();
		Usuario u2 = new Usuario();
		
		u.setUsuario("testPA1");		//debería ser false
		u2.setUsuario("testAD5");		//debería ser true
		
		try {
			if(gestionUsuarios.esAdministrativo(u)) {
				fail("El usuario debería no ser administrador");
			}
			
			if(!gestionUsuarios.esAdministrativo(u2)) {
				fail("El usuario debería ser administrador");
			}
			
		}catch (Exception e) {
			fail("No debería haber dado excepciones");
		}
		
	}
	
	
	@Test
	@Requisitos({"RF1"}) //Comprueba si un Usuario es administrativo o no, no existe y da error
	public void comprobarAdministrativoNoExistente() {
		Usuario u = new Usuario();
		u.setUsuario("Inventado");
		
		try {
			gestionUsuarios.esAdministrativo(u);
			fail("No debería haber encontrado usuario existente");
			
		}catch (UsuarioNoEncontradoException e) {
			
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}
		
	}
	
	@Test
	@Requisitos({"RF10"}) //Comrueba los datos de usuario de los clientes para poder hacer el login
	public void usuarioRegExistBien() {
			Usuario u = new Usuario();
			u.setUsuario("testPA1");
		
		try {
			assertEquals(u, gestionUsuarios.usuarioRegistrado("testPA1", "testPA1"));

		}catch (Exception e) {
			fail("No debería haber dado excepcion");
		}
		
	}
	
	@Test
	@Requisitos({"RF10"}) //Comrueba los datos de usuario de los clientes para poder hacer el login, esten mal
	public void usuarioRegiExistMal() {
		try {
			gestionUsuarios.usuarioRegistrado("testAD5", "fallo");
		
		}catch (ContraseñaIncorrectaException e) {
			
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}
	}
	
	@Test
	public void numUsuarios() {
		assertEquals(5, gestionUsuarios.sacarUsuarios().size());
	}
		
	
}
