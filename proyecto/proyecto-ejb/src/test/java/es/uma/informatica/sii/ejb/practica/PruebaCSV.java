package es.uma.informatica.sii.ejb.practica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;

import es.uma.proyecto.GestionGenerarReportes;

public class PruebaCSV {

	private static final String REPORTES_EJB = "java:global/classes/GenerarReportesEJB";
	private static final String UNIDAD_PERSITENCIA_PRUEBAS = "proyecto-ejb_TESTS";
	private GestionGenerarReportes gestionReportes;
	
	@Before
	public void setup() throws NamingException  {
		gestionReportes = (GestionGenerarReportes) SuiteTest.ctx.lookup(REPORTES_EJB);
		BaseDatos.inicializarBaseDeDatosPruebaCSV(UNIDAD_PERSITENCIA_PRUEBAS);
	}
	
	
	@Test
	public void CSVprimerSegundoTest() {
		List<List <String>> listasList = new ArrayList<>();
		List <String> linea1 = new ArrayList<>();
		linea1.add("IBANTESTSE1");
		linea1.add("Prueba2");
		linea1.add("X");
		linea1.add("X");
		linea1.add("X");
		linea1.add("1");
		linea1.add("X");
		linea1.add("4");
		linea1.add("noexistente");
		List <String> linea2 = new ArrayList<>();
		linea2.add("IBANTESTSE2");
		linea2.add("Prueba1A");
		linea2.add("Prueba1N");
		linea2.add("Prueba1D");
		linea2.add("Prueba1C");
		linea2.add("2");
		linea2.add("Prueba1P");
		linea2.add("1");
		linea2.add("noexistente");
		List <String> linea3 = new ArrayList<>();
		linea3.add("IBANTESTSE2");
		linea3.add("Prueba3A");
		linea3.add("Prueba3N");
		linea3.add("Prueba3D");
		linea3.add("Prueba3C");
		linea3.add("3");
		linea3.add("Prueba3P");
		linea3.add("2");
		linea3.add("noexistente");
		
		String nombre="";
		try {
			nombre=gestionReportes.generarReporteSegundo();
		} catch (Exception e) {
			fail("No debería haberse lanzado excepción "+e.getClass()+" "+e.getMessage());
		}

		
		try (
	            Reader reader = Files.newBufferedReader(Paths.get(nombre));
	            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
	                    .withFirstRecordAsHeader()
	                    .withIgnoreHeaderCase()
	                    .withTrim());
	        ){
			
			 for (CSVRecord csvRecord : csvParser) {
	                listasList.add(csvRecord.toList());
	            }
			 
			 assertEquals(3, listasList.size());
			 assertTrue(listasList.contains(linea1));
			 assertTrue(listasList.contains(linea2));
			 assertTrue(listasList.contains(linea3));
			
		}catch (Exception e) {
			fail("No debería haberse lanzado excepción");
		}
	}
	
	@Test
	public void CSVprimerReporteTest() {
		List<List <String>> listasList = new ArrayList<>();
		List <String> linea1 = new ArrayList<>();
		linea1.add("IBANTESTSE1");
		linea1.add("noexistente");
		linea1.add("noexistente");
		linea1.add("noexistente");
		linea1.add("noexistente");
		linea1.add("noexistente");
		linea1.add("noexistente");
		linea1.add("noexistente");
		linea1.add("noexistente");
		List <String> linea2 = new ArrayList<>();
		linea2.add("IBANTESTSE2");
		linea2.add("noexistente");
		linea2.add("noexistente");
		linea2.add("noexistente");
		linea2.add("noexistente");
		linea2.add("noexistente");
		linea2.add("noexistente");
		linea2.add("noexistente");
		linea2.add("noexistente");
		List <String> linea3 = new ArrayList<>();
		linea3.add("IBANTESTSE3");
		linea3.add("noexistente");
		linea3.add("noexistente");
		linea3.add("noexistente");
		linea3.add("noexistente");
		linea3.add("noexistente");
		linea3.add("noexistente");
		linea3.add("noexistente");
		linea3.add("noexistente");
		
		String nombre="";
		try {
			nombre=gestionReportes.generarReportePrimero();
		} catch (Exception e) {
			fail("No debería haberse lanzado excepción "+e.getClass()+" "+e.getMessage());
		}

		
		try (
	            Reader reader = Files.newBufferedReader(Paths.get(nombre));
	            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
	                    .withFirstRecordAsHeader()
	                    .withIgnoreHeaderCase()
	                    .withTrim());
	        ){
			
			 for (CSVRecord csvRecord : csvParser) {
	                listasList.add(csvRecord.toList());
	            }
			 
			 assertEquals(3, listasList.size());
			 assertTrue(listasList.contains(linea1));
			 assertTrue(listasList.contains(linea2));
			 assertTrue(listasList.contains(linea3));
			
		}catch (Exception e) {
			fail("No debería haberse lanzado excepción");
		}
	}
	
}
