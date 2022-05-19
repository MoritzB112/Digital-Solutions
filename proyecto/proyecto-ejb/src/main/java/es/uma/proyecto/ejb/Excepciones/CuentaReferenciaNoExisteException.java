package es.uma.proyecto.ejb.Excepciones;

public class CuentaReferenciaNoExisteException extends Exception {

	public CuentaReferenciaNoExisteException() {
		super();
	}
	
	public CuentaReferenciaNoExisteException(String msg) {
		super(msg);
	}
}
