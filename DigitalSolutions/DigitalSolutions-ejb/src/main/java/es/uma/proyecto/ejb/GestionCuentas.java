package es.uma.proyecto.ejb;

import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.ejb.Excepciones.ClienteNoExisteException;
import es.uma.proyecto.ejb.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.CuentaNoSuporteadaException;
import es.uma.proyecto.ejb.Excepciones.CuentaReferenciaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.CuentaYaExisteException;
import es.uma.proyecto.ejb.Excepciones.DivisaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.SaldoNoVacioException;
import es.uma.proyecto.jpa.Cliente;
import es.uma.proyecto.jpa.Cuenta;
import es.uma.proyecto.jpa.Cuenta_Fintech;
import es.uma.proyecto.jpa.Cuenta_Referencia;
import es.uma.proyecto.jpa.Divisa;
import es.uma.proyecto.jpa.Pooled_Account;
import es.uma.proyecto.jpa.Segregada;
import es.uma.proyecto.jpa.Transaccion;

@Local
public interface GestionCuentas {

	public void addCuenta(Cuenta_Fintech cf, Cuenta_Referencia cr, Cliente cl) throws CuentaYaExisteException,
			CuentaNoSuporteadaException, ClienteNoExisteException, CuentaReferenciaNoExisteException;
	

	public void addCuentaPooled(Pooled_Account pa, Cliente cl) throws CuentaYaExisteException, ClienteNoExisteException;
	
	public void abrirCuentaReferencia(Cuenta_Referencia cr, Divisa dv)
			throws CuentaYaExisteException, DivisaNoExisteException;
	
	public void cerrarCuenta(Cuenta_Fintech cu)
			throws CuentaNoExisteException, SaldoNoVacioException, CuentaNoSuporteadaException;

	public Cuenta sacarCuenta(Cuenta cu) throws CuentaNoExisteException, CuentaNoSuporteadaException;
	
	public List<Cuenta_Referencia> sacarCuentaReferencia();
	
	public List<Segregada> sacarSegregadas();

	public void añadirCartera(Pooled_Account pa, Cuenta_Referencia cr) throws CuentaNoExisteException;

	public List<Pooled_Account> sacarPooledAccount();
	
	public List<Transaccion> sacarTransacciones(Cuenta c) throws CuentaNoExisteException;
	
	public List<Cuenta_Fintech> sacarInformacionCuenta(String iban, String estado);

	public Segregada gtSegregada(Long id);
	
	public Pooled_Account gtPooled(Long id);
	
	public void modificarCuenta(Cuenta_Fintech c) throws CuentaNoExisteException, CuentaNoSuporteadaException;
}
