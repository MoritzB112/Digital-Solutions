package es.uma.proyecto;

import java.io.IOException;

import javax.ejb.Local;

@Local
public interface GestionGenerarReportes {
	
	public void generarReporteTodas() throws IOException;
	
	public void generarReporteActivos() throws IOException;
	
}
