package es.uma.proyecto.Excepciones;

public class Persona_AutorizadaNoEncontrada extends Exception {
	
	public Persona_AutorizadaNoEncontrada () {};
	
	public Persona_AutorizadaNoEncontrada(String message) {
		super(message);
	}

}