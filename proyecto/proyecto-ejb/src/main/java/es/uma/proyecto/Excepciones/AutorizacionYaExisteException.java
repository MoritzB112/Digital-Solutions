package es.uma.proyecto.Excepciones;

public class AutorizacionYaExisteException extends Exception {

	public AutorizacionYaExisteException() {
		super();
	}
	
	public AutorizacionYaExisteException(String msg) {
		super(msg);
	}
}
