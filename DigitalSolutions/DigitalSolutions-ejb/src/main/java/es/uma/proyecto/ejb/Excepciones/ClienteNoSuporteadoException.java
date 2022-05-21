package es.uma.proyecto.ejb.Excepciones;

public class ClienteNoSuporteadoException extends Exception {
	public ClienteNoSuporteadoException() {
		super();
	}
	
	public ClienteNoSuporteadoException(String s) {
		super(s);
	}

}
