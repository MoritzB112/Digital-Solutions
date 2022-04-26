package es.uma.proyecto.Excepciones;

public class TransaccionYaExisteException extends Exception {
	
	public TransaccionYaExisteException() {
		super();
	}
	
	public TransaccionYaExisteException(String msg) {
		super(msg);
	}
}
