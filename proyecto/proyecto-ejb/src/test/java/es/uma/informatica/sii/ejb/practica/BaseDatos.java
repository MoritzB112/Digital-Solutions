package es.uma.informatica.sii.ejb.practica;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.proyecto.Divisa;

public class BaseDatos {
	public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Divisa d1=new Divisa();
		d1.setAbreviatura("EUR");
		d1.setCambioEuro(1.0);
		d1.setNombre("EURO");
		em.persist(d1);
		
		Divisa d2=new Divisa();
		d2.setAbreviatura("USD");
		d2.setCambioEuro(1.6);
		d2.setNombre("US-DOLLAR");
		em.persist(d2);
		
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
