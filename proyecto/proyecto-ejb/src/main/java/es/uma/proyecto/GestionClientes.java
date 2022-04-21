package es.uma.proyecto;

import javax.ejb.Local;

@Local
public interface GestionClientes {
	
	public boolean esAdministrativo(Cliente cl);
	
	public Cliente clienteRegistrado(String usuario, String contraseña);
	
	public void darDeAlta(Cliente cl);
	
	public void modificarCliente(Cliente cl);
	
	public void darDeBaja(Cliente cl);

}
