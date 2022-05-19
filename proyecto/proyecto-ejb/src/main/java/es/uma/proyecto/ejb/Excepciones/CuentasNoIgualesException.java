package es.uma.proyecto.ejb.Excepciones;

public class CuentasNoIgualesException extends Exception {

	public CuentasNoIgualesException() {
		super();
	}
	
	public CuentasNoIgualesException(String msg) {
		super(msg);
	}
}
