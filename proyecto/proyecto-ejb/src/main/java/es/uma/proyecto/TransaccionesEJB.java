package es.uma.proyecto;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class TransaccionesEJB implements GestonTransacciones {

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;
	
	@Override
	public void crearTransaccion(Cuenta orig, Cuenta dest) {
		//TODO
	}

	@Override
	public List<Transaccion> sacarTransacciones() {
		return em.createQuery("SELECT tr FROM Transaccion tr", Transaccion.class).getResultList();
	}

}
