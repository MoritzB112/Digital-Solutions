package es.uma.proyecto;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.Excepciones.Persona_AutorizadaNoEncontrada;


@Stateless
public class Personas_AutorizadasEJB implements GestionPersonas_Autorizadas {
	
	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;

	
	@Override
	public void insertarPersonaAutorizada(Persona_Autorizada pa, Empresa cuenta, Autorizacion aut) {
		// TODO
		
	}

	@Override
	public void modificarDatosAutorizado(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontrada {
		if(em.find(Persona_Autorizada.class, pa.getID())==null) {
			throw new Persona_AutorizadaNoEncontrada("ERROR: No existe la persona autorizada a modificar.");
		}
		em.merge(pa);		
		
	}

	@Override
	public void eliminarAutorizadoCuenta(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontrada{
		if(em.find(Persona_Autorizada.class, pa.getID())==null) {
			throw new Persona_AutorizadaNoEncontrada("ERROR: No existe la persona autorizada a eliminar");
		}
		pa.setEstado("Eliminado");
		// Se debe mantener en la BD el objeto
		
	}

	@Override
	public void bloquearAutorizado(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontrada {
		if(em.find(Persona_Autorizada.class, pa.getID())==null) {
			throw new Persona_AutorizadaNoEncontrada("ERROR: No existe la persona autorizada a bloquear");
		}
		pa.setEstado("Bloqueado");
		
	}
	
}