package es.uma.proyecto.ejb.Excepciones;

public class UsuarioNoEncontradoException extends Exception {
	
	public UsuarioNoEncontradoException() {
		super();
	}
	
	public UsuarioNoEncontradoException(String s) {
		super(s);
	}
}
