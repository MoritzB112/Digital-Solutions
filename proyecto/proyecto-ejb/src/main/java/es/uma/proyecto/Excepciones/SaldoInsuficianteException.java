package es.uma.proyecto.Excepciones;

public class SaldoInsuficianteException extends Exception {

	public SaldoInsuficianteException() {
		super();
	}
	
	public SaldoInsuficianteException(String msg) {
		super(msg);
	}
}
