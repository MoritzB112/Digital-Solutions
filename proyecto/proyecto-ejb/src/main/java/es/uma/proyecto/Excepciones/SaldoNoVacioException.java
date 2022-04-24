package es.uma.proyecto.Excepciones;

public class SaldoNoVacioException extends Exception {

	public SaldoNoVacioException() {
		super();
	}
	
	public SaldoNoVacioException(String msg) {
		super(msg);
	}
}
