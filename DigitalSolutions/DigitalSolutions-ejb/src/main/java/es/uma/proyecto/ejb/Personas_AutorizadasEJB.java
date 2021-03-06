package es.uma.proyecto.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.ejb.Excepciones.AutorizacionNoExisteException;
import es.uma.proyecto.ejb.Excepciones.AutorizacionYaExisteException;
import es.uma.proyecto.ejb.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.Persona_AutorizadaNoEncontradaException;
import es.uma.proyecto.ejb.Excepciones.Persona_AutorizadaYaExisteException;
import es.uma.proyecto.ejb.Excepciones.UsuarioNoEncontradoException;
import es.uma.proyecto.jpa.Autorizacion;
import es.uma.proyecto.jpa.Autorizacion_PK;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Individual;
import es.uma.proyecto.jpa.Persona_Autorizada;
import es.uma.proyecto.jpa.Usuario;

@Stateless
public class Personas_AutorizadasEJB implements GestionPersonas_Autorizadas {

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;

	@Override
	public void insertarPersonaAutorizada(Usuario u, Persona_Autorizada pa)
			throws Persona_AutorizadaYaExisteException, UsuarioNoEncontradoException {
		if (pa.getID()!=null && (em.find(Persona_Autorizada.class, pa.getID()) != null)) {
			throw new Persona_AutorizadaYaExisteException();
		}
		Usuario ureal = em.find(Usuario.class, u.getUsuario());
		if (ureal == null) {
			throw new UsuarioNoEncontradoException();
		}

		pa.setUs(u);

		em.persist(pa);

	}

	public void darAutorizacion(Empresa emp, Autorizacion au, Persona_Autorizada pa)
			throws AutorizacionYaExisteException, CuentaNoExisteException, Persona_AutorizadaYaExisteException {
		
		Autorizacion_PK auPK = new Autorizacion_PK();
		auPK.setEmID(emp.getId());
		auPK.setPaID(pa.getID());
		au.setId(auPK);
		
		if (em.find(Autorizacion.class, au.getId()) != null) {
			throw new AutorizacionYaExisteException();
		}
		Empresa empreal = em.find(Empresa.class, emp.getId());
		if (empreal == null) {
			throw new CuentaNoExisteException();
		}
		Persona_Autorizada pareal = em.find(Persona_Autorizada.class, pa.getID());
		if (pareal == null) {
			throw new Persona_AutorizadaYaExisteException();
		}

		
		au.setEm(empreal);
		au.setPa(pareal);

		em.persist(au);

	}

	@Override
	public void modificarDatosAutorizado(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontradaException {
		if (em.find(Persona_Autorizada.class, pa.getID()) == null) {
			throw new Persona_AutorizadaNoEncontradaException("ERROR: No existe la persona autorizada a modificar.");
		}
		em.merge(pa);

	}

	@Override
	public void eliminarAutorizadoCuenta(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontradaException {
		Persona_Autorizada pareal = em.find(Persona_Autorizada.class, pa.getID());
		if (pareal == null) {
			throw new Persona_AutorizadaNoEncontradaException("ERROR: No existe la persona autorizada a eliminar");
		}
		pareal.setEstado("Baja");
		// Se debe mantener en la BD el objeto

	}

	@Override
	public void bloquearAutorizado(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontradaException {
		Persona_Autorizada pareal = em.find(Persona_Autorizada.class, pa.getID());
		if (pareal == null) {
			throw new Persona_AutorizadaNoEncontradaException("ERROR: No existe la persona autorizada a bloquear");
		}
		pareal.setEstado("Bloqueado");

	}
	
	public void desbloquearAutorizado(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontradaException {
		Persona_Autorizada pareal = em.find(Persona_Autorizada.class, pa.getID());
		if (pareal == null) {
			throw new Persona_AutorizadaNoEncontradaException("ERROR: No existe la persona autorizada a bloquear");
		}
		pareal.setEstado("ALTA");

	}
	
	public List<Persona_Autorizada> sacarPA(){
		return em.createQuery("SELECT paut FROM Persona_Autorizada paut", Persona_Autorizada.class).getResultList();
	}
	
	public List<Autorizacion> sacarAutorizaciones(){
		return em.createQuery("SELECT aut FROM Autorizacion aut", Autorizacion.class).getResultList();
	}
	
	public Persona_Autorizada gtPautorizada(Long id) {
		return em.find(Persona_Autorizada.class,id);
		}
	
	public void eliminarAutorizacion(Autorizacion a) throws AutorizacionNoExisteException {
		Autorizacion areal=em.find(Autorizacion.class, a.getId());
		
		if(areal==null) {
			throw new AutorizacionNoExisteException();
		}
		em.remove(areal);
	}
}