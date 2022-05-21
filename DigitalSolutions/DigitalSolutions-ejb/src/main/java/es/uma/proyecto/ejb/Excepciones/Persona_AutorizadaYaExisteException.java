package es.uma.proyecto.ejb.Excepciones;

public class Persona_AutorizadaYaExisteException extends Exception {

	public Persona_AutorizadaYaExisteException() {
		super();
	}
	
	public Persona_AutorizadaYaExisteException(String msg) {
		super(msg);
	}
}
