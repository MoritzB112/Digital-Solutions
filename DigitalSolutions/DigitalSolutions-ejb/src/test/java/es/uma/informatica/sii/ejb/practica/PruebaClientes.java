package es.uma.informatica.sii.ejb.practica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.informatica.sii.anotaciones.Requisitos;
import es.uma.proyecto.ejb.GestionClientes;
import es.uma.proyecto.ejb.Excepciones.ClienteExistenteException;
import es.uma.proyecto.ejb.Excepciones.ClienteNoExisteException;
import es.uma.proyecto.ejb.Excepciones.TieneCuentaAsociadoException;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Individual;
import es.uma.proyecto.jpa.Usuario;

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
	@Requisitos({"RF2"}) //Se da de alta un cliente de tipo empresa a la BBDD
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
			gestionClientes.darDeAltaEmpresa(emp);
			assertTrue(gestionClientes.sacarEmpresas().contains(emp));
			
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}	
	} 
	
	
	@Test
	@Requisitos({"RF2"}) //Se da de alta un cliente de tipo empresa ya existiente, fallara
	public void altaEmpresaRegTest() {
		Empresa emp = new Empresa();
		Usuario u = new Usuario();
		u.setUsuario("testPA1");
		emp.setId(1L);
		
		try {
			gestionClientes.darDeAltaEmpresa(emp);
			fail("No debería haber seguido");
			
		} catch (ClienteExistenteException e) {
			
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}
		
	}
	

	@Test
	@Requisitos({"RF2"}) //Se da de alta un cliente de tipo individual a la BBDD
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
	@Requisitos({"RF2"}) //Se da de alta un cliente de tipo individual y existente, fallara
	public void altaIndividualRegTest() {
		Empresa ind = new Empresa();
		Usuario u = new Usuario();
		u.setUsuario("testPA1");
		ind.setId(1L);
		
		try {
			gestionClientes.darDeAltaEmpresa(ind);
			fail("No debería haber seguido");
			
		} catch (ClienteExistenteException e) {
			
		}catch (Exception e) {
			fail("No debería haber dado excepción");
		}
		
	}
	
	
	@Test
	@Requisitos({"RF3"}) //Se modifica la informacion de un cliente en la BBDD
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
	@Requisitos({"RF3"}) //Se meodifica la informacion de un cliente ya existente y fallara
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
	@Requisitos({"RF4"}) //Da de baja una empresa existente sin eleminarlo
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
	@Requisitos({"RF4"}) //Da de baja una Empresa no existente y falla
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
	@Requisitos({"RF4"}) //Da de baja un individual existente sin eleminarlo
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
	@Requisitos({"RF4"}) //Da de baja un individual no existente, falla
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
	@Requisitos({"RF16"}) //Bloquea la cuenta de un cliente
	public void bloquearClienteTest() {
		Individual c=new Individual();
		c.setId(3L);
		Individual ind2=new Individual();

		try {
			gestionClientes.bloquearCliente(c);
			for(Individual i:gestionClientes.sacarIndividual()) {
				if(i.getId().equals(c.getId())) {
					ind2=i;
				}
			}
			assertEquals("BLOQUEADO", ind2.getEstado().toUpperCase());
		}catch (Exception e) {
			fail("no deberia haber lanzado excepcion");
		}
	}
	
	@Test
	@Requisitos({"RF16"}) //Desbloquea la cuenta de un cliente
	public void desbloquearClienteTest() {
		Individual c=new Individual();
		c.setId(3L);
		Individual ind2=new Individual();

		try {
			gestionClientes.bloquearCliente(c);
			gestionClientes.desbloquearCliente(c);
			for(Individual i:gestionClientes.sacarIndividual()) {
				if(i.getId().equals(c.getId())) {
					ind2=i;
				}
			}
			assertEquals("ALTA", ind2.getEstado().toUpperCase());
		}catch (Exception e) {
			fail("no deberia haber lanzado excepcion");
		}
	}
	
	@Test
	@Requisitos({"RF11"}) //Saca la informacion necesaria para generar el reporte
	public void numIndividuales() {
		assertEquals(2, gestionClientes.sacarIndividual().size());
	}
	
	@Test
	@Requisitos({"RF11"}) //Saca la informacion necesara para los reportes
	public void numEmpresas() {
		assertEquals(2, gestionClientes.sacarEmpresas().size());
	}
	
}
