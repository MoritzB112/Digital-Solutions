package es.uma.proyecto.ejb.Excepciones;

public class ContraseñaIncorrectaException extends Exception {
	
	public ContraseñaIncorrectaException() {
		super();
	}
	
	public ContraseñaIncorrectaException(String s) {
		super(s);
	}

}
