package es.uma.proyecto;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.Excepciones.AutorizacionYaExisteException;
import es.uma.proyecto.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.Excepciones.Persona_AutorizadaNoEncontrada;
import es.uma.proyecto.Excepciones.Persona_AutorizadaYaExisteException;
import es.uma.proyecto.Excepciones.UsuarioNoEncontradoException;


@Stateless
public class Personas_AutorizadasEJB implements GestionPersonas_Autorizadas {
	
	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;

	
	@Override
	public void insertarPersonaAutorizada(Usuario u,Persona_Autorizada pa) throws Persona_AutorizadaYaExisteException, UsuarioNoEncontradoException {
		if(em.find(Persona_Autorizada.class, pa.getID())!=null) {
			throw new Persona_AutorizadaYaExisteException();
		}
		Usuario ureal=em.find(Usuario.class, u.getUsuario());
		if(ureal==null) {
			throw new UsuarioNoEncontradoException();
		}
		pa.setUs(u);
		em.persist(pa);
		u.setPa(pa);
	}
	
	public void darAutorizacion(Empresa emp, Autorizacion au, Persona_Autorizada pa)
			throws AutorizacionYaExisteException, CuentaNoExisteException, Persona_AutorizadaYaExisteException {
		if(em.find(Autorizacion.class, au.getId())!=null) {
			throw new AutorizacionYaExisteException();
		}
		Empresa empreal=em.find(Empresa.class, emp.getId());
		if(empreal==null) {
			throw new CuentaNoExisteException();
		}
		Persona_Autorizada pareal=em.find(Persona_Autorizada.class, pa.getID());
		if(pareal==null) {
			throw new Persona_AutorizadaYaExisteException();
		}
		
		Autorizacion_PK auPK=new Autorizacion_PK();
		au.setId(auPK);
		au.setEm(empreal);
		au.setPa(pareal);
		
		em.persist(au);
		
		auPK.setEmID(empreal.getId());
		auPK.setPaID(pareal.getID());
		
		
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
		Persona_Autorizada pareal=em.find(Persona_Autorizada.class, pa.getID());
		if(pareal==null) {
			throw new Persona_AutorizadaNoEncontrada("ERROR: No existe la persona autorizada a eliminar");
		}
		pareal.setEstado("Eliminado");
		// Se debe mantener en la BD el objeto
		
	}

	@Override
	public void bloquearAutorizado(Persona_Autorizada pa) throws Persona_AutorizadaNoEncontrada {
		Persona_Autorizada pareal=em.find(Persona_Autorizada.class, pa.getID());
		if(pareal==null) {
			throw new Persona_AutorizadaNoEncontrada("ERROR: No existe la persona autorizada a bloquear");
		}
		pareal.setEstado("Bloqueado");
		
	}
	
}