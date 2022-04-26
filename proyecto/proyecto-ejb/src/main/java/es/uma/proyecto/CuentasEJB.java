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
			de.setSaldo(0.0);
			de.setCr(crreal);
			de.setPa(pa);
			Depositado_en_PK dePK = new Depositado_en_PK();
			dePK.setCrID(crreal.getIBAN());
			dePK.setPaID(pa.getIBAN());
			de.setId(dePK);
			List<Depositado_en> l = new ArrayList<>();
			l.add(de);
			
			pa.setCl(clreal);
			pa.setDeps(l);
			
			List<Depositado_en> l2 = crreal.getDeps();
			l2.add(de);
			crreal.setDeps(l2);

			em.persist(pa);
			em.persist(dePK);
			em.persist(de);

		} else if (cf instanceof Segregada) {
			Segregada se = (Segregada) cf;
			if (em.find(Segregada.class, cr.getIBAN()) != null) {
				throw new RuntimeException();
			}
			Cliente clreal = em.find(Cliente.class, cl.getId());
			if (clreal == null) {
				throw new ClienteNoExisteException();
			}
			Cuenta_Referencia crreal = em.find(Cuenta_Referencia.class, cr.getIBAN());
			if (crreal == null) {
				throw new CuentaReferenciaNoExisteException();
			}

			se.setCr(crreal);
			se.setCl(clreal);

			em.persist(se);

		} else {
			throw new CuentaNoSuporteadaException();
		}
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
	
	public List<Pooled_Account> sacarPooledAccounts() {
		return em.createQuery("SELECT pa FROM Pooled_Account pa", Pooled_Account.class).getResultList();
	}
	
	public List<Segregada> sacarSegregadas() {
		return em.createQuery("SELECT pa FROM Segregada pa", Segregada.class).getResultList();
	}

	@Override
	public List<Pooled_Account> sacarSegregada() {
		// TODO Auto-generated method stub
		return null;
	}
}
