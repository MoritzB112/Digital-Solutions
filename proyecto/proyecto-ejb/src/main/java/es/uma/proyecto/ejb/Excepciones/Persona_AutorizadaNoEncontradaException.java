package es.uma.proyecto.ejb.Excepciones;

public class Persona_AutorizadaNoEncontradaException extends Exception {
	
	public Persona_AutorizadaNoEncontradaException () {};
	
	public Persona_AutorizadaNoEncontradaException(String message) {
		super(message);
	}

}