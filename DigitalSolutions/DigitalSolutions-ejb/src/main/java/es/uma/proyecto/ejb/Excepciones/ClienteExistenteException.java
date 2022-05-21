package es.uma.proyecto.ejb.Excepciones;

public class ClienteExistenteException extends Exception {
	
	public ClienteExistenteException() {
		super();
	}
	
	public ClienteExistenteException(String s) {
		super(s);
	}

}
