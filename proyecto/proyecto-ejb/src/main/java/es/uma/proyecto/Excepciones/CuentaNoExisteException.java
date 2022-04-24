package es.uma.proyecto.Excepciones;

public class CuentaNoExisteException extends Exception {

	public CuentaNoExisteException() {
		super();
	}
	
	public CuentaNoExisteException(String msg) {
		super(msg);
	}
}
