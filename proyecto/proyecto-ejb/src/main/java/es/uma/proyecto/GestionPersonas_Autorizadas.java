package es.uma.proyecto;

import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.Excepciones.AutorizacionYaExisteException;
import es.uma.proyecto.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.Excepciones.Persona_AutorizadaNoEncontradaException;
import es.uma.proyecto.Excepciones.Persona_AutorizadaYaExisteException;
import es.uma.proyecto.Excepciones.UsuarioNoEncontradoException;

@Local
public interface GestionPersonas_Autorizadas {
	
	public void insertarPersonaAutorizada(Usuario u,Persona_Autorizada pa) throws Persona_AutorizadaYaExisteException, UsuarioNoEncontradoException;
	
	public void darAutorizacion(Empresa em, Autorizacion au, Persona_Autorizada pa)
			throws AutorizacionYaExisteException, CuentaNoExisteException, Persona_AutorizadaYaExisteException;
	
	public void modificarDatosAutorizado(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontradaException;
	
	public void eliminarAutorizadoCuenta(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontradaException;
	
	public void bloquearAutorizado(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontradaException;
	
	public List<Persona_Autorizada> sacarPA();
	
	public List<Autorizacion> sacarAutorizaciones();
}