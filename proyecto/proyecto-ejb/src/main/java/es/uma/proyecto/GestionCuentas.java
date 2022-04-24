package es.uma.proyecto;

import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.Excepciones.ClienteNoExisteException;
import es.uma.proyecto.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.Excepciones.CuentaNoSuporteadaException;
import es.uma.proyecto.Excepciones.CuentaReferenciaNoExisteException;
import es.uma.proyecto.Excepciones.CuentaYaExisteException;
import es.uma.proyecto.Excepciones.SaldoNoVacioException;

@Local
public interface GestionCuentas {

	public void addCuenta(Cuenta_Fintech cf, Cuenta_Referencia cr, Cliente cl) throws CuentaYaExisteException,
			CuentaNoSuporteadaException, ClienteNoExisteException, CuentaReferenciaNoExisteException;

	public void cerrarCuenta(Cuenta_Fintech cu)
			throws CuentaNoExisteException, SaldoNoVacioException, CuentaNoSuporteadaException;

	public Cuenta sacarCuenta(Cuenta cu) throws CuentaNoExisteException, CuentaNoSuporteadaException;
	
	public List<Pooled_Account> sacarPooledAccounts();
	
	public List<Pooled_Account> sacarSegregada();

}
