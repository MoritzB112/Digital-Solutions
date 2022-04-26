package es.uma.proyecto.Excepciones;

public class DivisaNoExisteException extends Exception {
	
	public DivisaNoExisteException() {
		super();
	}
	
	public DivisaNoExisteException(String msg) {
		super(msg);
	}
}
