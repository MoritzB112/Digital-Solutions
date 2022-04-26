package es.uma.proyecto;

import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.Excepciones.ClienteNoExisteException;
import es.uma.proyecto.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.Excepciones.CuentaNoSuporteadaException;
import es.uma.proyecto.Excepciones.CuentaReferenciaNoExisteException;
import es.uma.proyecto.Excepciones.CuentaYaExisteException;
import es.uma.proyecto.Excepciones.DivisaNoExisteException;
import es.uma.proyecto.Excepciones.SaldoNoVacioException;

@Local
public interface GestionCuentas {

	public void addCuenta(Cuenta_Fintech cf, Cuenta_Referencia cr, Cliente cl) throws CuentaYaExisteException,
			CuentaNoSuporteadaException, ClienteNoExisteException, CuentaReferenciaNoExisteException;

	public void abrirCuentaReferencia(Cuenta_Referencia cr, Divisa dv)
			throws CuentaYaExisteException, DivisaNoExisteException;
	
	public void cerrarCuenta(Cuenta_Fintech cu)
			throws CuentaNoExisteException, SaldoNoVacioException, CuentaNoSuporteadaException;

	public Cuenta sacarCuenta(Cuenta cu) throws CuentaNoExisteException, CuentaNoSuporteadaException;
	
	public List<Cuenta_Referencia> sacarCuentaReferencia();
	
	public List<Segregada> sacarSegregadas();

	public void addCuenta(Pooled_Account pa, Cuenta_Referencia cr) throws CuentaNoExisteException;

	public List<Pooled_Account> sacarPooledAccount();
	
	public List<Transaccion> sacarTransacciones(Cuenta c);

}
