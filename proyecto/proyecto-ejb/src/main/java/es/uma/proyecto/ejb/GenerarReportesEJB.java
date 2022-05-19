package es.uma.proyecto.ejb;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import es.uma.proyecto.jpa.Autorizacion;
import es.uma.proyecto.jpa.Cliente;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Individual;
import es.uma.proyecto.jpa.Persona_Autorizada;
import es.uma.proyecto.jpa.Segregada;


@Stateless
public class GenerarReportesEJB implements GestionGenerarReportes {

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;
	
	@EJB
	GestionCuentas cEJB;

	@Override
	public String generarReportePrimero() throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMYYYYHHmmss");
		Date date = new Date(System.currentTimeMillis());
		String dateS = formatter.format(date);
		BufferedWriter writer = Files.newBufferedWriter(Paths.get("Ebury_IBAN_"+dateS+".csv"));

		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("IBAN", "Last_Name", "First_Name",
				"Street", "City", "Post_Code", "Country", "identification_Number", "Date_Of_Birth"));
		for (Segregada c : cEJB.sacarSegregadas()) {
			csvPrinter.printRecord(c.getIBAN(), "noexistente", "noexistente", "noexistente", "noexistente",
					"noexistente", "noexistente", "noexistente", "noexistente");
			csvPrinter.flush();
		}
		return "Ebury_IBAN_"+dateS+".csv";
	}

	@Override
//	@Schedule(dayOfWeek = "Mon")
	public String generarReporteSegundo() throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMYYYYHHmmss");
		SimpleDateFormat cumple = new SimpleDateFormat("YYYY-MM-dd");
		Date date = new Date(System.currentTimeMillis());
		String dateS = formatter.format(date);
		BufferedWriter writer = Files.newBufferedWriter(Paths.get("Ebury_IBAN_"+dateS+".csv"));

		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("IBAN", "Last_Name", "First_Name",
				"Street", "City", "Post_Code", "Country", "identification_Number", "Date_Of_Birth"));
		
		for (Segregada c : cEJB.sacarSegregadas()) {
			Cliente cl = c.getCl();
			if (c.getEstado().equalsIgnoreCase("ALTA")) {
				if (cl instanceof Empresa) {
					Empresa emp = (Empresa) cl;
					for (Autorizacion a : emp.getAu()) {
						Persona_Autorizada pa = a.getPa();
						if (pa.getEstado().equalsIgnoreCase("ALTA")) {
							String apellido = pa.getApellidos() == null ? "noexistente" : pa.getApellidos();
							String nombre = pa.getNombre() == null ? "noexistente" : pa.getNombre();
							String direccion = pa.getDireccion() == null ? "noexistente" : pa.getDireccion();
							String ciudad = pa.getCiudad() == null ? "noexistente" : pa.getCiudad();
							String cp = pa.getCodigoPostal() == null ? "noexistente" : pa.getCodigoPostal().toString();
							String pais = pa.getPais() == null ? "noexistente" : pa.getPais();
							String id = pa.getID() == null ? "noexistente" : pa.getID().toString();
							String fecha_naciminto = pa.getFecha_nacimiento() == null ? "noexistente"
									: cumple.format(pa.getFecha_nacimiento());
							csvPrinter.printRecord(c.getIBAN(), apellido, nombre, direccion, ciudad, cp, pais, id,
									fecha_naciminto);
							csvPrinter.flush();
						}
					}
				} else if (cl instanceof Individual) {
					Individual id = (Individual) cl;
					String apellido = id.getApellido() == null ? "noexistente" : id.getApellido();
					String nombre = id.getNombre() == null ? "noexistente" : id.getNombre();
					String direccion = id.getDireccion() == null ? "noexistente" : id.getDireccion();
					String ciudad = id.getCiudad() == null ? "noexistente" : id.getCiudad();
					String cp = id.getCodigoPostal() == null ? "noexistente" : id.getCodigoPostal().toString();
					String pais = id.getPais() == null ? "noexistente" : id.getPais();
					String ID = id.getId() == null ? "noexistente" : id.getId().toString();
					String fecha_nacimiento = id.getFecha_nacimiento() == null ? "noexistente"
							: cumple.format(id.getFecha_nacimiento());
					csvPrinter.printRecord(c.getIBAN(), apellido, nombre, direccion, ciudad, cp, pais, ID,
							fecha_nacimiento);				
					csvPrinter.flush();
				}
			}
		}
		return "Ebury_IBAN_"+dateS+".csv";
	}
}
