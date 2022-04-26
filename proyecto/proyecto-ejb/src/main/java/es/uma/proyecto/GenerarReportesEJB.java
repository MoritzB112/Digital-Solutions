package es.uma.proyecto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.Schedule;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import es.uma.proyecto.Excepciones.CuentaNoSuporteadaException;

public class GenerarReportesEJB implements GestionGenerarReportes {

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;

	@Override
	public void generarReporteActivos() throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMYYYYHHmmss");
		Date date = new Date(System.currentTimeMillis());
		String dateS = formatter.format(date);
		CuentasEJB cEJB = new CuentasEJB();
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(dateS));

		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("IBAN", "Last_Name", "First_Name",
				"Street", "City", "Post_Code", "Country", "identification_Number", "Date_Of_Birth"));
		for (Segregada c : cEJB.sacarSegregadas()) {
			csvPrinter.printRecord(c.getIBAN(), "noexistente", "noexistente", "noexistente", "noexistente",
					"noexistente", "noexistente", "noexistente", "noexistente");
		}
		csvPrinter.flush();
		
		for (Cuenta_Referencia c : cEJB.sacarCuentaReferencia()) {
			csvPrinter.printRecord(c.getIBAN(), "noexistente", "Fnitech", "noexistente", "noexistente", "noexistente",
					"noexistente", "noexistente", "noexistente");
		}
		csvPrinter.flush();
	}

	@Override
	@Schedule(dayOfWeek = "Mon")
	public void generarReporteTodas() throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMYYYYHHmmss");
		SimpleDateFormat cumple = new SimpleDateFormat("YYYY-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		String dateS = formatter.format(date);
		CuentasEJB cEJB = new CuentasEJB();
		BufferedWriter writer = Files.newBufferedWriter(Paths.get(dateS));

		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("IBAN", "Last_Name", "First_Name",
				"Street", "City", "Post_Code", "Country", "identification_Number", "Date_Of_Birth"));
		for (Segregada c : cEJB.sacarSegregadas()) {
			Cliente cl = c.getCl();
			if (cl.getEstado().equalsIgnoreCase("ALTA")) {
				if (cl instanceof Empresa) {
					Empresa emp = (Empresa) cl;
					for (Autorizacion a : emp.getAu()) {
						Persona_Autorizada pa = a.getPa();
						if (pa.getEstado().equalsIgnoreCase("ALTA")) {
							csvPrinter.printRecord(c.getIBAN(), pa.getApellidos(), pa.getNombre(), pa.getDireccion(),
									pa.getCiudad(), pa.getCodigoPostal(), pa.getPais(), pa.getID(),
									pa.getFecha_nacimiento());
						}
					}
				} else if (cl instanceof Individual) {
					Individual id = (Individual) cl;
					csvPrinter.printRecord(c.getIBAN(), id.getApellido(), id.getNombre(), id.getDireccion(),
							id.getCiudad(), id.getCodigoPostal(), id.getPais(), id.getId(), id.getFecha_nacimiento());
				}
			}

		}
		csvPrinter.flush();

		for (Cuenta_Referencia c : cEJB.sacarCuentaReferencia()) {
			csvPrinter.printRecord(c.getIBAN(), "noexistente", "Fnitech", "noexistente", "noexistente", "noexistente",
					"noexistente", "noexistente", "noexistente");
		}
		csvPrinter.flush();
	}
}
