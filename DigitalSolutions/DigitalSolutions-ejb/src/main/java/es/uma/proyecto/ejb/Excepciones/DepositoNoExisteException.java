package es.uma.proyecto.ejb.Excepciones;

public class DepositoNoExisteException extends Exception {

	public DepositoNoExisteException() {
		super();
	}
	
	public DepositoNoExisteException(String msg) {
		super(msg);
	}
}
