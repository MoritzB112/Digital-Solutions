package es.uma.informatica.sii.ejb.practica;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import es.uma.proyecto.Cliente;
import es.uma.proyecto.GestionClientes;
import es.uma.proyecto.GestionDivisas;
import es.uma.proyecto.GestionUsuarios;
import es.uma.proyecto.Usuario;
import es.uma.proyecto.Excepciones.ClienteExistenteException;
import es.uma.proyecto.Excepciones.ClienteNoSuporteadoException;
import es.uma.proyecto.Excepciones.PasswordException;
import es.uma.proyecto.Excepciones.UsuarioExistenteException;
import es.uma.proyecto.Excepciones.UsuarioNoEncontradoException;

public class Trazabilidad {
	
	private static final Logger LOG = Logger.getLogger(Trazabilidad.class.getCanonicalName());

	private static final String LOTES_EJB = "java:global/classes/ClientesEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejb_TESTS";
	
	private GestionClientes gestionDivisas;
	
	private GestionUsuarios gesUsuarios;
	
	
	
	@Before
	public void setup() throws NamingException  {
		gestionDivisas = (GestionClientes) SuiteTest.ctx.lookup(LOTES_EJB);
		gesUsuarios = (GestionUsuarios) SuiteTest.ctx.lookup("java:global/classes/UsuariosEJB");
		BaseDatos.inicializaBaseDatos(UNIDAD_PERSITENCIA_PRUEBAS);
	}

	@Test
	public void testTest() {
//		Usuario u=new Usuario();
//		u.setEsAdministrativo(false);
//		u.setUsuario("Test");
//		u.setPassword("hola");
//		
//		Cliente cl=new Cliente();
//		cl.setEstado("alta");
		
		try {
//			gesUsuarios.crearUsuario(u);
//			gestionDivisas.darDeAlta(u, cl);
			File f=new File("test.txt");
			FileWriter fw=new FileWriter(f);
			fw.write("Test");
			fw.close();
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
