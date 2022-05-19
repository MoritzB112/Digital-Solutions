package es.uma.proyecto.ejb.Excepciones;

public class SaldoInsuficianteException extends Exception {

	public SaldoInsuficianteException() {
		super();
	}
	
	public SaldoInsuficianteException(String msg) {
		super(msg);
	}
}
