package es.uma.proyecto.Excepciones;

public class DepositoNoExisteException extends Exception {

	public DepositoNoExisteException() {
		super();
	}
	
	public DepositoNoExisteException(String msg) {
		super(msg);
	}
}
