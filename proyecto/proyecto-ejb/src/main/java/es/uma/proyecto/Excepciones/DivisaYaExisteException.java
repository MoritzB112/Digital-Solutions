package es.uma.proyecto.Excepciones;

public class DivisaYaExisteException extends Exception {
	
	public DivisaYaExisteException() {
		super();
	}
	
	public DivisaYaExisteException(String msg) {
		super(msg);
	}
}
