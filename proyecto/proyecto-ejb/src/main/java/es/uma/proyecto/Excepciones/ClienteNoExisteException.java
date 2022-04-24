package es.uma.proyecto.Excepciones;

public class ClienteNoExisteException extends Exception {

	public ClienteNoExisteException() {
		super();
	}
	
	public ClienteNoExisteException(String msg) {
		super(msg);
	}
}
