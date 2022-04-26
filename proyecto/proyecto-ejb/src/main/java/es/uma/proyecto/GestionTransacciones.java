package es.uma.proyecto;

import java.util.List;

import es.uma.proyecto.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.Excepciones.DivisaNoExisteException;
import es.uma.proyecto.Excepciones.TransaccionYaExisteException;

public interface GestionTransacciones {

	public void crearTransaccion(Transaccion t, Pooled_Account paOR, Pooled_Account paDEST, Divisa divOR,
			Divisa divDEST) throws CuentaNoExisteException, DivisaNoExisteException, TransaccionYaExisteException;

	public List<Transaccion> sacarTransacciones();

}
