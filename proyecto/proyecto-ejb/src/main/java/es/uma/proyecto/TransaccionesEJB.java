package es.uma.proyecto;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.Excepciones.CuentasNoIgualesException;
import es.uma.proyecto.Excepciones.DepositoNoExisteException;
import es.uma.proyecto.Excepciones.SaldoInsuficianteException;
import es.uma.proyecto.Excepciones.TransaccionYaExisteException;

@Stateless
public class TransaccionesEJB implements GestionTransacciones {

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;

	@Override
	public void cambioDivisa(Transaccion t, Depositado_en dep1, Depositado_en dep2)
			throws TransaccionYaExisteException, DepositoNoExisteException, CuentasNoIgualesException,
			SaldoInsuficianteException {
		if (em.find(Transaccion.class, t.getID_unico()) != null) {
			throw new TransaccionYaExisteException();
		}
		Depositado_en dep1real = em.find(Depositado_en.class, dep1.getId());
		if (dep1real == null) {
			throw new DepositoNoExisteException("dep1");
		}
		Depositado_en dep2real = em.find(Depositado_en.class, dep2.getId());
		if (dep2real == null) {
			throw new DepositoNoExisteException("dep2");
		}
		if (!dep1real.getPa().equals(dep2real.getPa())) {
			throw new CuentasNoIgualesException();
		}
		if (t.getCantidad() + t.getComision() > dep1real.getSaldo()) {
			throw new SaldoInsuficianteException();
		}

		t.setDivEm(dep1real.getCr().getDiv());
		t.setDivRec(dep2real.getCr().getDiv());

		t.setOrigen(dep1real.getPa());
		t.setDestino(dep2real.getPa());

		t.setTipo("CAMBIO_DIVISA");
		em.persist(t);

		dep1real.setSaldo(dep1real.getSaldo() - t.getCantidad() - t.getComision());
		Double dineroDestino = (t.getCantidad() * dep1real.getCr().getDiv().getCambioEuro())
				/ dep2real.getCr().getDiv().getCambioEuro();
		dep2real.setSaldo(dep2real.getSaldo() + dineroDestino);

	}

	@Override
	public List<Transaccion> sacarTransacciones() {
		return em.createQuery("SELECT tr FROM Transaccion tr", Transaccion.class).getResultList();
	}

}
