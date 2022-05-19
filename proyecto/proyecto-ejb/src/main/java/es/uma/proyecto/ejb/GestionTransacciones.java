package es.uma.proyecto.ejb;

import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.ejb.Excepciones.CuentasNoIgualesException;
import es.uma.proyecto.ejb.Excepciones.DepositoNoExisteException;
import es.uma.proyecto.ejb.Excepciones.SaldoInsuficianteException;
import es.uma.proyecto.ejb.Excepciones.TransaccionYaExisteException;
import es.uma.proyecto.jpa.Depositado_en;
import es.uma.proyecto.jpa.Transaccion;

@Local
public interface GestionTransacciones {

	public void cambioDivisa(Transaccion t, Depositado_en dep1, Depositado_en depDEST)
			throws TransaccionYaExisteException, DepositoNoExisteException, CuentasNoIgualesException,
			SaldoInsuficianteException;

	public List<Transaccion> sacarTransacciones();

}
