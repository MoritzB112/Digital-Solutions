package es.uma.proyecto;

import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.Excepciones.CuentasNoIgualesException;
import es.uma.proyecto.Excepciones.DepositoNoExisteException;
import es.uma.proyecto.Excepciones.SaldoInsuficianteException;
import es.uma.proyecto.Excepciones.TransaccionYaExisteException;

@Local
public interface GestionTransacciones {

	public void cambioDivisa(Transaccion t, Depositado_en dep1, Depositado_en depDEST)
			throws TransaccionYaExisteException, DepositoNoExisteException, CuentasNoIgualesException,
			SaldoInsuficianteException;

	public List<Transaccion> sacarTransacciones();

}
