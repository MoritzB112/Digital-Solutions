package es.uma.proyecto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CuentasEJB implements GestionCuentas {
	
	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;

	@Override
	public void addCuenta(Cuenta_Fintech cf, Cuenta_Referencia cr, Cliente cl) {
		if(cf instanceof Pooled_Account) {
			Pooled_Account pa = (Pooled_Account)cf;
			if(em.find(Pooled_Account.class, cr.getIBAN())!=null) {
				throw new RuntimeException();
			}
			//arreglar cliente y cuenta referencia
			
			Depositado_en de = new Depositado_en();
			de.setSaldo(0.0);
			de.setCr(cr);
			de.setPa(pa);
			Depositado_en_PK dePK = new Depositado_en_PK();
			dePK.setCrID(cr.getIBAN());
			dePK.setPaID(pa.getIBAN());
			de.setId(dePK);
			List<Depositado_en> l = new ArrayList<>();
			l.add(de);
			pa.setCl(cl);
			pa.setDeps(l);
			List<Depositado_en> l2 = cr.getDeps();
			l2.add(de);
			cr.setDeps(l2);
			
			em.persist(pa);
			em.persist(dePK);
			em.persist(de);
			
		}else if(cf instanceof Segregada) {
			Segregada se = (Segregada)cf;
			if(em.find(Segregada.class, cr.getIBAN())!=null) {
				throw new RuntimeException();
			}
			
			se.setCr(cr);
			
			em.persist(se);
			
		}else {
			throw new RuntimeException();
		}
	}
	
	@Override
	public void cerrarCuenta(Cuenta_Fintech cu) {
		if(cu instanceof Segregada) {
			Segregada se = em.find(Segregada.class, cu.getIBAN());
			if(se==null) {
				throw new RuntimeException();
			}
			
			if(se.getCr().getSaldo()==0.0) {
				se.setEstado("BAJA");
			}else {
				throw new RuntimeException();
			}
			
		}else if(cu instanceof Pooled_Account) {
			Pooled_Account pa = em.find(Pooled_Account.class, cu.getIBAN());
			if(pa==null) {
				throw new RuntimeException();
			}
			
			for(Depositado_en de:pa.getDeps()) {
				if(de.getSaldo()!=0.0) {
					throw new RuntimeException();
				}
			}
			pa.setEstado("BAJA");
			
		}else {
			throw new RuntimeException();
		}
	}
	
	@Override
	public Cuenta sacarCuenta(Cuenta cu) {
		if(cu instanceof Segregada) {
			Segregada se = em.find(Segregada.class, cu.getIBAN());
			if(se==null) {
				throw new RuntimeException();
			}
			
			return se;
			
		}else if(cu instanceof Pooled_Account) {
			Pooled_Account pa = em.find(Pooled_Account.class, cu.getIBAN());
			if(pa==null) {
				throw new RuntimeException();
			}
			
			return pa;
			
		}else if(cu instanceof Cuenta_Referencia){
			Cuenta_Referencia cr = em.find(Cuenta_Referencia.class, cu.getIBAN());
			if(cr==null) {
				throw new RuntimeException();
			}
			
			return cr;
			
		}else {
			throw new RuntimeException();
		}
		
	}

}
