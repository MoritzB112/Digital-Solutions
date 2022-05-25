package es.uma.proyecto.ejb;

import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.ejb.Excepciones.ClienteExistenteException;
import es.uma.proyecto.ejb.Excepciones.ClienteNoExisteException;
import es.uma.proyecto.ejb.Excepciones.ClienteNoSuporteadoException;
import es.uma.proyecto.ejb.Excepciones.TieneCuentaAsociadoException;
import es.uma.proyecto.ejb.Excepciones.UsuarioNoEncontradoException;
import es.uma.proyecto.jpa.Cliente;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Individual;
import es.uma.proyecto.jpa.Segregada;
import es.uma.proyecto.jpa.Usuario;

@Local
public interface GestionClientes {

	
	public void darDeAltaEmpresa(Empresa e) throws ClienteExistenteException;
	
	public void darDeAltaIndividual(Usuario u, Individual e) throws ClienteExistenteException;
	
	public void modificarCliente(Cliente cl) throws ClienteNoExisteException, ClienteNoSuporteadoException;
	
	public void darDeBajaIndividual(Individual i) throws ClienteNoExisteException, TieneCuentaAsociadoException;
	
	public void darDeBajaEmpresa(Empresa e) throws ClienteNoExisteException, TieneCuentaAsociadoException;
	
	public void bloquearCliente(Cliente cl) throws ClienteNoExisteException, ClienteNoSuporteadoException;
	
	public void desbloquearCliente(Cliente cl) throws ClienteNoExisteException, ClienteNoSuporteadoException;
	
	public List<Cliente> sacarClientes();
	
	public List<Empresa> sacarEmpresas ();
	
	public List<Individual> sacarIndividual();
	
	public Individual gtIndividual(Long id);
	
	public Empresa gtEmpresa(Long id);

}
