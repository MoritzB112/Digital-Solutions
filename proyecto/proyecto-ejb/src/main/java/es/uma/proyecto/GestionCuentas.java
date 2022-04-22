package es.uma.proyecto;

import javax.ejb.Local;

@Local
public interface GestionCuentas {

	public void addCuenta(Cuenta_Fintech cf, Cuenta_Referencia cr, Cliente cl);
	
	public void cerrarCuenta(Cuenta_Fintech cu);
	
	public Cuenta sacarCuenta(Cuenta cu);
	
}
