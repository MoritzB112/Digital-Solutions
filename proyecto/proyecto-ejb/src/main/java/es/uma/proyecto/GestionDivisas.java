package es.uma.proyecto;

import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.Excepciones.DivisaNoExisteException;
import es.uma.proyecto.Excepciones.DivisaYaExisteException;

@Local
public interface GestionDivisas {
	
	public void insertarDivisa(Divisa div) throws DivisaYaExisteException;
	
	public void modificarDivisa(Divisa div) throws DivisaNoExisteException;
	
	public List<Divisa> getDivisas();
	
	public void actualizarDivisas();

}
