package es.uma.informatica.sii.ejb.practica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.Empresa;
import es.uma.proyecto.GestionClientes;
import es.uma.proyecto.Individual;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.Excepciones.ClienteExistenteException;
import es.uma.proyecto.Excepciones.ClienteNoExisteException;
import es.uma.proyecto.Excepciones.TieneCuentaAsociadoException;

public class PruebaClientes {

	private static final String CLIENTES_EJB = "java:global/classes/ClientesEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejb_TESTS";
	private GestionClientes gestionClientes;
	
	@Before
	public void setup() throws NamingException  {
		gestionClientes = (GestionClientes) SuiteTest.ctx.lookup(CLIENTES_EJB);
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}

	@Test
	public void altaEmpresaNoRegTest() {
		Empresa emp = new Empresa();
		Usuario u = new Usuario();
		u.setUsuario("testPA1");
		
		emp.setId(10L);
		emp.setRazon_social("parado");
		emp.setEstado("activo");
		emp.setDireccion("ejemplo");
		emp.setCiudad("Malaga");
		emp.setCodigoPostal(29010);
		emp.setPais("España");
		emp.setFecha_alta(new Date());

		
		try {
			gestionClientes.darDeAltaEmpresa(u, emp);
			assertTrue(gestionClientes.sacarEmpresas().contains(emp));
			
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}	
	} 
	
	
	@Test
	public void altaEmpresaRegTest() {
		Empresa emp = new Empresa();
		Usuario u = new Usuario();
		u.setUsuario("testPA1");
		emp.setId(1L);
		
		try {
			gestionClientes.darDeAltaEmpresa(u, emp);
			fail("No debería haber seguido");
			
		} catch (ClienteExistenteException e) {
			
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}
		
	}
	

	@Test
	public void altaIndividualNoRegTest() {
		Individual ind = new Individual();
		Usuario u = new Usuario();
		u.setUsuario("testPA1");
		
		ind.setId(10L);
		ind.setEstado("activo");
		ind.setDireccion("ejemplo");
		ind.setCiudad("Malaga");
		ind.setCodigoPostal(29010);
		ind.setPais("España");
		ind.setFecha_alta(new Date());
		ind.setApellido("inventado");
		ind.setNombre("inventado");
		

		
		try {
			gestionClientes.darDeAltaIndividual(u, ind);
			assertTrue(gestionClientes.sacarIndividual().contains(ind));
			
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}	
	} 
	
	
	@Test
	public void altaIndividualRegTest() {
		Empresa ind = new Empresa();
		Usuario u = new Usuario();
		u.setUsuario("testPA1");
		ind.setId(1L);
		
		try {
			gestionClientes.darDeAltaEmpresa(u, ind);
			fail("No debería haber seguido");
			
		} catch (ClienteExistenteException e) {
			
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}
		
	}
	
	
	@Test
	public void modificarClienteIndExistTest() {
		Individual ind = gestionClientes.sacarIndividual().get(0);
		Individual ind2 = null;
		ind.setApellido("inventado");
		
		try {
			gestionClientes.modificarCliente(ind);
			for(Individual x : gestionClientes.sacarIndividual()) {
				if(x.getId().equals(ind.getId())) {
					ind2 = x;
				}
			}
			assertEquals(ind.getApellido(), ind2.getApellido());
		}catch(Exception e) {
			fail("No debería haber dado excepcion");
		}	
	}
	
	@Test
	public void modificarClienteIndNoExistTest() {
		Individual ind = new Individual();
		ind.setId(10L);
		ind.setApellido("inventado");
		
		try {
			gestionClientes.modificarCliente(ind);
			fail("No debería haber continunado");
		}catch(ClienteNoExisteException e) {
			
		}catch (Exception e) {
			fail("No debería haber habido excepción");
		}
	}
	
	@Test
	public void bajaEmpresaExistTest() {
		Empresa emp = new Empresa();
		emp.setId(2L);
		
		try {
			gestionClientes.darDeBajaEmpresa(emp);
			assertTrue(gestionClientes.sacarEmpresas().contains(emp));
			Empresa emp2 = null;
			for(Empresa x : gestionClientes.sacarEmpresas()) {
				if(x.getId().equals(emp.getId())) {
					emp2 = x;
				}
			}
			assertEquals("BAJA", emp2.getEstado().toUpperCase());
			
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}	
	}
	
	@Test
	public void bajaEmpresaNoExist() {
		Empresa emp = new Empresa();
		emp.setId(1L);
		
		try {
			gestionClientes.darDeBajaEmpresa(emp);
			fail("No debería haber continuado");
		} catch (TieneCuentaAsociadoException e) {
			
		} catch (Exception e) {
			fail("No debería dar excepción");
		}
	}
	
	
	@Test
	public void bajaIndiviExistTest() {
		Individual ind = new Individual();
		ind.setId(4L);
		
		try {
			gestionClientes.darDeBajaIndividual(ind);
			assertTrue(gestionClientes.sacarIndividual().contains(ind));
			Individual ind2 = null;
			for(Individual x : gestionClientes.sacarIndividual()) {
				if(x.getId().equals(ind.getId())) {
					ind2 = x;
				}
			}
			assertEquals("BAJA", ind2.getEstado().toUpperCase());
			
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}	
	}
	
	@Test
	public void bajaIndiviNoExist() {
		Individual ind = new Individual();
		ind.setId(3L);
		
		try {
			gestionClientes.darDeBajaIndividual(ind);
			fail("No debería haber continuado");
		} catch (TieneCuentaAsociadoException e) {
			
		} catch (Exception e) {
			fail("No debería dar excepción");
		}
	}
	
	@Test
	public void numIndividuales() {
		assertEquals(2, gestionClientes.sacarIndividual().size());
	}
	
	@Test
	public void numEmpresas() {
		assertEquals(2, gestionClientes.sacarEmpresas().size());
	}
	
}
