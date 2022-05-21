package es.uma.proyecto.ejb.Excepciones;

public class DivisaNoExisteException extends Exception {
	
	public DivisaNoExisteException() {
		super();
	}
	
	public DivisaNoExisteException(String msg) {
		super(msg);
	}
}
