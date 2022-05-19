package es.uma.proyecto.ejb.Excepciones;

public class DivisaYaExisteException extends Exception {
	
	public DivisaYaExisteException() {
		super();
	}
	
	public DivisaYaExisteException(String msg) {
		super(msg);
	}
}
