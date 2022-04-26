package es.uma.proyecto.Excepciones;

public class UsuarioExistenteException extends Exception {

	public UsuarioExistenteException() {
		super();
	}
	
	public UsuarioExistenteException(String s) {
		super(s);
	}
}
