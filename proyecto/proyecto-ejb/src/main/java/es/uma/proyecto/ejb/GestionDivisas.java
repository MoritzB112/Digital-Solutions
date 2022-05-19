package es.uma.proyecto.ejb;

import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.ejb.Excepciones.DivisaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.DivisaYaExisteException;
import es.uma.proyecto.jpa.Divisa;

@Local
public interface GestionDivisas {
	
	public void insertarDivisa(Divisa div) throws DivisaYaExisteException;
	
	public void modificarDivisa(Divisa div) throws DivisaNoExisteException;
	
	public List<Divisa> getDivisas();
	
	public void actualizarDivisas();

}
