package es.uma.proyecto.ejb;

import javax.ejb.Local;

import es.uma.proyecto.ejb.Excepciones.ClienteNoExisteException;
import es.uma.proyecto.ejb.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.Persona_AutorizadaNoEncontradaException;
import es.uma.proyecto.ejb.Excepciones.UsuarioNoEncontradoException;
import es.uma.proyecto.jpa.Cuenta_Fintech;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Individual;
import es.uma.proyecto.jpa.Persona_Autorizada;
import es.uma.proyecto.jpa.Usuario;

@Local
public interface GestionRefresh {

	public Usuario refUsu(Usuario u) throws UsuarioNoEncontradoException;

	public Individual refInd(Individual u) throws ClienteNoExisteException;

	public Empresa refEmp(Empresa u) throws ClienteNoExisteException;

	public Persona_Autorizada refPa(Persona_Autorizada u) throws Persona_AutorizadaNoEncontradaException;

	public Cuenta_Fintech refCf(Cuenta_Fintech u) throws CuentaNoExisteException;

}
