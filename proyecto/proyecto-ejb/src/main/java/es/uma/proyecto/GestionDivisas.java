package es.uma.proyecto;

import javax.ejb.Local;

@Local
public interface GestionDivisas {
	
	public void insertarDivisa(Divisa div);
	
	public void modificarDivisa(Divisa div);
	
	public void eliminarDivisa(Divisa div);
	
	public void actualizarDivisas();

}
