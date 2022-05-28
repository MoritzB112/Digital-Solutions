package es.uma.proyecto.ejb.Excepciones;

public class AutorizacionNoExisteException extends Exception {

	public AutorizacionNoExisteException() {
		super();
	}
	
	public AutorizacionNoExisteException(String s) {
		super(s);
	}
}
