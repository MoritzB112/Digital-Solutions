package es.uma.proyecto;

import javax.ejb.Local;

import es.uma.proyecto.Excepciones.AutorizacionYaExisteException;
import es.uma.proyecto.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.Excepciones.Persona_AutorizadaNoEncontrada;
import es.uma.proyecto.Excepciones.Persona_AutorizadaYaExisteException;
import es.uma.proyecto.Excepciones.UsuarioNoEncontradoException;

@Local
public interface GestionPersonas_Autorizadas {
	
	public void insertarPersonaAutorizada(Usuario u,Persona_Autorizada pa) throws Persona_AutorizadaYaExisteException, UsuarioNoEncontradoException;
	
	public void darAutorizacion(Empresa em, Autorizacion au, Persona_Autorizada pa)
			throws AutorizacionYaExisteException, CuentaNoExisteException, Persona_AutorizadaYaExisteException;
	
	public void modificarDatosAutorizado(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontrada;
	
	public void eliminarAutorizadoCuenta(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontrada;
	
	public void bloquearAutorizado(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontrada;
}