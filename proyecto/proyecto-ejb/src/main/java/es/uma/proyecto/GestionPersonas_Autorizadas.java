package es.uma.proyecto;

import javax.ejb.Local;

import es.uma.proyecto.Excepciones.Persona_AutorizadaNoEncontrada;

@Local
public interface GestionPersonas_Autorizadas {
	
	void insertarPersonaAutorizada(Persona_Autorizada pa, Empresa cuenta, Autorizacion aut);
	
	void modificarDatosAutorizado(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontrada;
	
	void eliminarAutorizadoCuenta(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontrada;
	
	void bloquearAutorizado(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontrada;
}