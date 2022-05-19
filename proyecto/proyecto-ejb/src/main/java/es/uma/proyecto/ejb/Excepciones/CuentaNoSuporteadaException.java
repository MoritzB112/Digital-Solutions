package es.uma.proyecto.ejb.Excepciones;

public class CuentaNoSuporteadaException extends Exception {
	
	public CuentaNoSuporteadaException() {
		super();
	}

	public CuentaNoSuporteadaException(String msg) {
		super(msg);
	}
}
