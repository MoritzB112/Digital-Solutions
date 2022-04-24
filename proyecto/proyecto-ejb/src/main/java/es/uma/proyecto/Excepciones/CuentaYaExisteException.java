package es.uma.proyecto.Excepciones;

public class CuentaYaExisteException extends Exception {

	public CuentaYaExisteException() {
		super();
	}
	
	public CuentaYaExisteException(String msg) {
		super(msg);
	}
}
