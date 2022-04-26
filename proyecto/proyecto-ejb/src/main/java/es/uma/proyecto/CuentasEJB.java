package es.uma.proyecto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.Excepciones.*;

public class CuentasEJB implements GestionCuentas {

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;

	@Override
	public void addCuenta(Cuenta_Fintech cf, Cuenta_Referencia cr, Cliente cl) throws CuentaYaExisteException,
			CuentaNoSuporteadaException, ClienteNoExisteException, CuentaReferenciaNoExisteException {
		if (cf instanceof Pooled_Account) {
			Pooled_Account pa = (Pooled_Account) cf;
			if (em.find(Pooled_Account.class, cr.getIBAN()) != null) {
				throw new CuentaYaExisteException();
			}
			Cliente clreal = em.find(Cliente.class, cl.getId());
			if (clreal == null) {
				throw new ClienteNoExisteException();
			}
			Cuenta_Referencia crreal = em.find(Cuenta_Referencia.class, cr.getIBAN());
			if (crreal == null) {
				throw new CuentaReferenciaNoExisteException();
			}

			Depositado_en de = new Depositado_en();
			Depositado_en_PK dePK = new Depositado_en_PK();
			de.setId(dePK);
			
			em.persist(pa);
			em.persist(dePK);
			em.persist(de);
			
			
			de.setSaldo(0.0);
			de.setCr(crreal);
			de.setPa(pa);
			
			dePK.setCrID(crreal.getIBAN());
			dePK.setPaID(pa.getIBAN());
			
			
			List<Depositado_en> l = new ArrayList<>();
			l.add(de);
			
			pa.setCl(clreal);
			pa.setDeps(l);
			
			crreal.getDeps().add(de);
			

		} else if (cf instanceof Segregada) {
			Segregada se = (Segregada) cf;
			if (em.find(Segregada.class, cr.getIBAN()) != null) {
				throw new CuentaYaExisteException();
			}
			Cliente clreal = em.find(Cliente.class, cl.getId());
			if (clreal == null) {
				throw new ClienteNoExisteException();
			}
			Cuenta_Referencia crreal = em.find(Cuenta_Referencia.class, cr.getIBAN());
			if (crreal == null) {
				throw new CuentaReferenciaNoExisteException();
			}
			
			em.persist(se);
			
			se.setCr(crreal);
			se.setCl(clreal);
			
			clreal.getCf().add(se);
			cr.setSe(se);

		} else {
			throw new CuentaNoSuporteadaException();
		}
	}
	
	@Override
	public void addCuenta(Pooled_Account pa, Cuenta_Referencia cr) throws CuentaNoExisteException {
		Pooled_Account pareal=em.find(Pooled_Account.class, pa.getIBAN());
		if(pareal==null) {
			throw new CuentaNoExisteException("POOLED_ACCOUNT");
		}
		Cuenta_Referencia crreal=em.find(Cuenta_Referencia.class, cr.getIBAN());
		if(crreal==null) {
			throw new CuentaNoExisteException("CUENTA_REFERENCIA");
		}
		
		Depositado_en de=new Depositado_en();
		Depositado_en_PK dePK=new Depositado_en_PK();
		
		de.setId(dePK);
		dePK.setCrID(crreal.getIBAN());
		dePK.setPaID(pareal.getIBAN());
		
		em.persist(dePK);
		em.persist(de);
		
		de.setSaldo(0.0);
		de.setCr(crreal);
		de.setPa(pareal);
		
		crreal.getDeps().add(de);
		pareal.getDeps().add(de);
	}
	
	@Override
	public void abrirCuentaReferencia(Cuenta_Referencia cr) throws CuentaYaExisteException {
		if(em.find(Cuenta_Referencia.class, cr.getIBAN())!=null) {
			throw new CuentaYaExisteException();
		}
		em.persist(cr);
	}

	@Override
	public void cerrarCuenta(Cuenta_Fintech cu)
			throws CuentaNoExisteException, SaldoNoVacioException, CuentaNoSuporteadaException {
		if (cu instanceof Segregada) {
			Segregada se = em.find(Segregada.class, cu.getIBAN());
			if (se == null) {
				throw new CuentaNoExisteException();
			}

			if (se.getCr().getSaldo() == 0.0) {
				se.setEstado("BAJA");
			} else {
				throw new SaldoNoVacioException();
			}

		} else if (cu instanceof Pooled_Account) {
			Pooled_Account pa = em.find(Pooled_Account.class, cu.getIBAN());
			if (pa == null) {
				throw new CuentaNoExisteException();
			}

			for (Depositado_en de : pa.getDeps()) {
				if (de.getSaldo() != 0.0) {
					throw new SaldoNoVacioException();
				}
			}
			pa.setEstado("BAJA");

		} else {
			throw new CuentaNoSuporteadaException();
		}
	}

	@Override
	public Cuenta sacarCuenta(Cuenta cu) throws CuentaNoExisteException, CuentaNoSuporteadaException {
		if (cu instanceof Segregada) {
			Segregada se = em.find(Segregada.class, cu.getIBAN());
			if (se == null) {
				throw new CuentaNoExisteException();
			}

			return se;

		} else if (cu instanceof Pooled_Account) {
			Pooled_Account pa = em.find(Pooled_Account.class, cu.getIBAN());
			if (pa == null) {
				throw new CuentaNoExisteException();
			}

			return pa;

		} else if (cu instanceof Cuenta_Referencia) {
			Cuenta_Referencia cr = em.find(Cuenta_Referencia.class, cu.getIBAN());
			if (cr == null) {
				throw new CuentaNoExisteException();
			}

			return cr;

		} else {
			throw new CuentaNoSuporteadaException();
		}
	}
	
	@Override
	public List<Transaccion> sacarTransacciones(Cuenta_Fintech cf){
		//TODO
	}
	
	@Override
	public List<Cuenta_Referencia> sacarCuentaReferencia() {
		return em.createQuery("SELECT cr FROM Cuenta_Referencia cr", Cuenta_Referencia.class).getResultList();
	}
	
	@Override
	public List<Segregada> sacarSegregadas() {
		return em.createQuery("SELECT se FROM Segregada se", Segregada.class).getResultList();
	}
}
