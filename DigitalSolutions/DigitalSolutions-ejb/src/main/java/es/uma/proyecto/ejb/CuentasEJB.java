package es.uma.proyecto.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.uma.proyecto.ejb.Excepciones.ClienteNoExisteException;
import es.uma.proyecto.ejb.Excepciones.ClienteNoSuporteadoException;
import es.uma.proyecto.ejb.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.CuentaNoSuporteadaException;
import es.uma.proyecto.ejb.Excepciones.CuentaReferenciaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.CuentaYaExisteException;
import es.uma.proyecto.ejb.Excepciones.DivisaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.SaldoNoVacioException;
import es.uma.proyecto.jpa.Cliente;
import es.uma.proyecto.jpa.Cuenta;
import es.uma.proyecto.jpa.Cuenta_Fintech;
import es.uma.proyecto.jpa.Cuenta_Referencia;
import es.uma.proyecto.jpa.Depositado_en;
import es.uma.proyecto.jpa.Depositado_en_PK;
import es.uma.proyecto.jpa.Divisa;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Individual;
import es.uma.proyecto.jpa.Pooled_Account;
import es.uma.proyecto.jpa.Segregada;
import es.uma.proyecto.jpa.Transaccion;

@Stateless
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

			pa.setDeps(new ArrayList<>());

			em.persist(pa);

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

			se.setCr(crreal);
			se.setCl(clreal);

			em.persist(se);

		} else {
			throw new CuentaNoSuporteadaException();
		}
	}

	@Override
	public void añadirCartera(Pooled_Account pa, Cuenta_Referencia cr) throws CuentaNoExisteException {
		Pooled_Account pareal = em.find(Pooled_Account.class, pa.getIBAN());
		if (pareal == null) {
			throw new CuentaNoExisteException("POOLED_ACCOUNT");
		}
		Cuenta_Referencia crreal = em.find(Cuenta_Referencia.class, cr.getIBAN());
		if (crreal == null) {
			throw new CuentaNoExisteException("CUENTA_REFERENCIA");
		}

		Depositado_en de = new Depositado_en();
		Depositado_en_PK dePK = new Depositado_en_PK();

		dePK.setCrID(crreal.getIBAN());
		dePK.setPaID(pareal.getIBAN());

		de.setId(dePK);
		de.setSaldo(0.0);
		de.setCr(crreal);
		de.setPa(pareal);

		em.persist(de);

	}
	
	public void addCuentaPooled(Pooled_Account pa, Cliente cl) throws CuentaYaExisteException, ClienteNoExisteException {
		if (em.find(Pooled_Account.class, pa.getIBAN()) != null) {
			throw new CuentaYaExisteException();
		}
		Cliente clreal = em.find(Cliente.class, cl.getId());
		if (clreal == null) {
			throw new ClienteNoExisteException();
		}
		
		pa.setCl(clreal);
		em.persist(pa);
	}
	

	@Override
	public void abrirCuentaReferencia(Cuenta_Referencia cr, Divisa dv)
			throws CuentaYaExisteException, DivisaNoExisteException {
		if (em.find(Cuenta_Referencia.class, cr.getIBAN()) != null) {
			throw new CuentaYaExisteException();
		}
		Divisa dvreal = em.find(Divisa.class, dv.getAbreviatura());
		if (dvreal == null) {
			throw new DivisaNoExisteException();
		}

		cr.setDiv(dvreal);
		cr.setSaldo(0.0);

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
	public List<Transaccion> sacarTransacciones(Cuenta c) throws CuentaNoExisteException {
		if (c instanceof Segregada) {
			Segregada se = em.find(Segregada.class, c.getIBAN());
			if (se == null) {
				throw new CuentaNoExisteException("SEGREGADA");
			}
			List<Transaccion> l = se.getCobros();
			l.addAll(se.getPagos());
			return l;
		} else if (c instanceof Pooled_Account) {
			Pooled_Account se = em.find(Pooled_Account.class, c.getIBAN());
			if (se == null) {
				throw new CuentaNoExisteException("POOLED_ACCOUNT");
			}
			List<Transaccion> l = se.getCobros();
			l.addAll(se.getPagos());
			return l;
		} else if (c instanceof Cuenta_Referencia) {
			Cuenta_Referencia se = em.find(Cuenta_Referencia.class, c.getIBAN());
			if (se == null) {
				throw new CuentaNoExisteException("CUENTA_REFERENCIA");
			}
			List<Transaccion> l = se.getCobros();
			l.addAll(se.getPagos());
			return l;
		} else {
			Cuenta se = em.find(Cuenta.class, c.getIBAN());
			if (se == null) {
				throw new CuentaNoExisteException("CUENTA_REFERENCIA");
			}
			List<Transaccion> l = se.getCobros();
			l.addAll(se.getPagos());
			return l;
		}
	}
	
	@Override
	public List<Cuenta_Fintech> sacarInformacionCuenta(String iban, String estado) {
		List<Cuenta_Fintech> result;
		String consulta = "SELECT c FROM Cuenta_Fintech c";
		String s_iban;
		String s_estado;
		
		if (iban != null) {
			s_iban = "";
			
		} else if (estado == null) {
			s_estado = "";
			
		} else {
			
		}
		return null;
	}

	@Override
	public List<Cuenta_Referencia> sacarCuentaReferencia() {
		return em.createQuery("SELECT cr FROM Cuenta_Referencia cr", Cuenta_Referencia.class).getResultList();
	}

	@Override
	public List<Segregada> sacarSegregadas() {
		return em.createQuery("SELECT se FROM Segregada se", Segregada.class).getResultList();
	}

	@Override
	public List<Pooled_Account> sacarPooledAccount() {
		return em.createQuery("SELECT pa FROM Pooled_Account pa", Pooled_Account.class).getResultList();
	}
	
	public Segregada gtSegregada(Long id) {
		return em.find(Segregada.class,id);
		}
	
	public Pooled_Account gtPooled(Long id) {
		return em.find(Pooled_Account.class,id);
		}
	
	
}
