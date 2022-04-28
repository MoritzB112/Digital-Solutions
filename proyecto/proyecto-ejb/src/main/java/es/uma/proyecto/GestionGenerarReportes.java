package es.uma.proyecto;

import java.io.IOException;

import javax.ejb.Local;

@Local
public interface GestionGenerarReportes {
	
	public String generarReportePrimero() throws IOException;
	
	public String generarReporteSegundo() throws IOException;
	
}
