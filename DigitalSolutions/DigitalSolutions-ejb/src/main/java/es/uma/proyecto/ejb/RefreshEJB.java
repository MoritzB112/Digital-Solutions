package es.uma.proyecto.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.ejb.Excepciones.ClienteNoExisteException;
import es.uma.proyecto.ejb.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.Persona_AutorizadaNoEncontradaException;
import es.uma.proyecto.ejb.Excepciones.UsuarioNoEncontradoException;
import es.uma.proyecto.jpa.Cuenta_Fintech;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Individual;
import es.uma.proyecto.jpa.Persona_Autorizada;
import es.uma.proyecto.jpa.Pooled_Account;
import es.uma.proyecto.jpa.Segregada;
import es.uma.proyecto.jpa.Usuario;

@Stateless
public class RefreshEJB implements GestionRefresh {
	
	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;
	
	@Override
	public Usuario refUsu(Usuario u) throws UsuarioNoEncontradoException {
		Usuario u2=em.find(Usuario.class, u.getUsuario());
		if(u2==null) {
			throw new UsuarioNoEncontradoException(); 
		}
		em.refresh(u2);
		return u2;
	}
	
	@Override
	public Individual refInd(Individual u) throws ClienteNoExisteException {
		Individual u2=em.find(Individual.class, u.getId());

		if(u2==null) {
			throw new ClienteNoExisteException(); 
		}
		em.refresh(u2);
		return u2;
	}
	
	@Override
	public Empresa refEmp(Empresa u) throws ClienteNoExisteException {
		Empresa u2=em.find(Empresa.class, u.getId());

		if(u2==null) {
			throw new ClienteNoExisteException(); 
		}
		em.refresh(u2);
		return u2;
	}
	
	@Override
	public Persona_Autorizada refPa(Persona_Autorizada u) throws Persona_AutorizadaNoEncontradaException {
		Persona_Autorizada u2=em.find(Persona_Autorizada.class, u.getID());
		
		if(u2==null) {
			throw new Persona_AutorizadaNoEncontradaException(); 
		}
		em.refresh(u2);
		return u2;
	}
	
	@Override
	public Cuenta_Fintech refCf(Cuenta_Fintech u) throws CuentaNoExisteException {
		if(u instanceof Segregada) {
			Segregada se= em.find(Segregada.class, u.getIBAN());
			if(se==null) {
				throw new CuentaNoExisteException(); 
			}
			em.refresh(se);
			return se;
		}else if(u instanceof Pooled_Account) {
			Pooled_Account se= em.find(Pooled_Account.class, u.getIBAN());
			if(se==null) {
				throw new CuentaNoExisteException(); 
			}
			em.refresh(se);
			return se;
		}
		Cuenta_Fintech se= em.find(Cuenta_Fintech.class, u.getIBAN());

		if(se==null) {
			throw new CuentaNoExisteException(); 
		}
		em.refresh(se);
		return se;
	}
}
