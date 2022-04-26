package es.uma.proyecto;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.Excepciones.DivisaNoExisteException;
import es.uma.proyecto.Excepciones.TransaccionYaExisteException;

public class TransaccionesEJB implements GestionTransacciones {

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;
	
	@Override
	public void crearTransaccion(Transaccion t, Pooled_Account paOR, Pooled_Account paDEST, Divisa divOR,
			Divisa divDEST) throws CuentaNoExisteException, DivisaNoExisteException, TransaccionYaExisteException {
		if(em.find(Transaccion.class, t.getID_unico())!=null) {
			throw new TransaccionYaExisteException();
		}
		Pooled_Account paORreal=em.find(Pooled_Account.class, paOR.getIBAN());
		if(paORreal==null) {
			throw new CuentaNoExisteException("paOR");
		}
		Pooled_Account paDESTreal=em.find(Pooled_Account.class, paDEST.getIBAN());
		if(paDESTreal==null) {
			throw new CuentaNoExisteException("paDEST");
		}
		Divisa divORreal=em.find(Divisa.class, divOR.getAbreviatura());
		if(divORreal==null) {
			throw new DivisaNoExisteException();
		}
		Divisa divDESTreal=em.find(Divisa.class, divDEST.getAbreviatura());
		if(divDESTreal==null) {
			throw new DivisaNoExisteException();
		}
		t.setDestino(paDESTreal);
		t.setDivEm(divORreal);
		t.setDivRec(divDESTreal);
		t.setOrigen(paORreal);
		
		em.persist(t);
		
		paDESTreal.getPagos().add(t);
		paORreal.getCobros().add(t);
		divDESTreal.getDivPago().add(t);
		divORreal.getDivCobro().add(t);
	}

	@Override
	public List<Transaccion> sacarTransacciones() {
		return em.createQuery("SELECT tr FROM Transaccion tr", Transaccion.class).getResultList();
	}

}
