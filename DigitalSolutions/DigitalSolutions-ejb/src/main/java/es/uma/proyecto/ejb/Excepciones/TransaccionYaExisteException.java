package es.uma.proyecto.ejb.Excepciones;

public class TransaccionYaExisteException extends Exception {
	
	public TransaccionYaExisteException() {
		super();
	}
	
	public TransaccionYaExisteException(String msg) {
		super(msg);
	}
}
