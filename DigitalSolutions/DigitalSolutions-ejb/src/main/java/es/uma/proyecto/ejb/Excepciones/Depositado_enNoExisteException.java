package es.uma.proyecto.ejb.Excepciones;

public class Depositado_enNoExisteException extends Exception {

	public Depositado_enNoExisteException() {
		super();
	}
	
	public Depositado_enNoExisteException(String s) {
		super(s);
	}
}
