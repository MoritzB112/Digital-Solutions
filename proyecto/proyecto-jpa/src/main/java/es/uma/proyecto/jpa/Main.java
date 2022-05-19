package es.uma.proyecto.jpa;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto-jpa");
		EntityManager em = emf.createEntityManager();
		
		
		em.close();
		emf.close();
	}

}
