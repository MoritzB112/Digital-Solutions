package es.uma.proyecto.Excepciones;

public class Persona_AutorizadaYaExisteException extends Exception {

	public Persona_AutorizadaYaExisteException() {
		super();
	}
	
	public Persona_AutorizadaYaExisteException(String msg) {
		super(msg);
	}
}
