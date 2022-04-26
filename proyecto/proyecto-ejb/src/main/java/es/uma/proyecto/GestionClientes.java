package es.uma.proyecto;

import javax.ejb.Local;

import es.uma.proyecto.Excepciones.ClienteExistenteException;
import es.uma.proyecto.Excepciones.ClienteNoExisteException;
import es.uma.proyecto.Excepciones.ClienteNoSuporteadoException;
import es.uma.proyecto.Excepciones.UsuarioNoEncontradoException;

@Local
public interface GestionClientes {

	
	public void darDeAltaEmpresa(Usuario u, Empresa e) throws ClienteExistenteException;
	
	public void darDeAltaIndividual(Usuario u, Individual e) throws ClienteExistenteException;
	
	public void modificarCliente(Cliente cl) throws ClienteNoExisteException, ClienteNoSuporteadoException;
	
	public void darDeBajaIndividual(Individual i) throws ClienteExistenteException, TieneCuentaAsociadoException;
	
	public void darDeBajaEmpresa(Empresa e) throws ClienteExistenteException, TieneCuentaAsociadoException;

}
