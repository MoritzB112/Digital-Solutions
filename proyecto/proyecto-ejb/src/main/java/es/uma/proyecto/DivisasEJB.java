package es.uma.proyecto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DivisasEJB implements GestionDivisas {
	
	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;
	
	@Override
	public void insertarDivisa(Divisa div) {
		if(!em.contains(div)) {
			em.persist(div);
		}
	}
	
	@Override
	public void modificarDivisa(Divisa div) {
		Divisa realDiv=em.find(Divisa.class, div.getAbreviatura());
		if(realDiv!=null) {
			em.merge(div);
		}
		
	}
	
	@Override
	public void eliminarDivisa(Divisa div) {
		Divisa realDiv=em.find(Divisa.class, div.getAbreviatura());
		if(realDiv!=null) {
			em.remove(realDiv);
		}
	}
	
	@Override
	@Schedule(hour = "11")
	public void actualizarDivisas() {
		//acceder a api y acturalizar todas las divisas que hay
		List<Divisa> divs=em.createQuery("SELECT d FROM Divisa d", Divisa.class).getResultList();
		
	    
	}
}
