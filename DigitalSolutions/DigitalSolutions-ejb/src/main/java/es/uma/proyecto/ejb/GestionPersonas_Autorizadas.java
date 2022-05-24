package es.uma.proyecto.ejb;

import java.util.List;

import javax.ejb.Local;

import es.uma.proyecto.ejb.Excepciones.AutorizacionYaExisteException;
import es.uma.proyecto.ejb.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.Persona_AutorizadaNoEncontradaException;
import es.uma.proyecto.ejb.Excepciones.Persona_AutorizadaYaExisteException;
import es.uma.proyecto.ejb.Excepciones.UsuarioNoEncontradoException;
import es.uma.proyecto.jpa.Autorizacion;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Persona_Autorizada;
import es.uma.proyecto.jpa.Usuario;

@Local
public interface GestionPersonas_Autorizadas {
	
	public void insertarPersonaAutorizada(Usuario u,Persona_Autorizada pa) throws Persona_AutorizadaYaExisteException, UsuarioNoEncontradoException;
	
	public void darAutorizacion(Empresa em, Autorizacion au, Persona_Autorizada pa)
			throws AutorizacionYaExisteException, CuentaNoExisteException, Persona_AutorizadaYaExisteException;
	
	public void modificarDatosAutorizado(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontradaException;
	
	public void eliminarAutorizadoCuenta(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontradaException;
	
	public void bloquearAutorizado(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontradaException;
	
	public void desbloquearAutorizado(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontradaException;
	
	public List<Persona_Autorizada> sacarPA();
	
	public List<Autorizacion> sacarAutorizaciones();
	
	public Persona_Autorizada gtPautorizada(Long id);
}