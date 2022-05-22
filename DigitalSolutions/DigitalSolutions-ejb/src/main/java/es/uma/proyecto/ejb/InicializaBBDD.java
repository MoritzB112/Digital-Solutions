package es.uma.proyecto.ejb;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.ejb.Excepciones.PasswordException;
import es.uma.proyecto.jpa.Usuario;

@Singleton
@Startup
public class InicializaBBDD {
	
	private static SecureRandom sm = new SecureRandom();

	@PersistenceContext(name = "proyecto-ejb")
	private EntityManager em;
	
	@PostConstruct
	public void inicializar() {
		

		if(em.find(Usuario.class, "testU")!=null) {
			return;
		}
		
		Usuario u=new Usuario();
		u.setCorreo("testU@test.test");
		u.setEsAdministrativo(false);
//		u.setSalt(getSalt());
//		u.setPassword(hashPasswword(u.getSalt(), "test"));
		u.setPassword("test");
		u.setUsuario("testU");
		em.persist(u);
		
		Usuario a=new Usuario();
        a.setCorreo("testU@admin.test");
        a.setEsAdministrativo(true);
//        a.setSalt(getSalt());
//        a.setPassword(hashPasswword(a.getSalt(), "admin"));
        u.setPassword("test");
        a.setUsuario("admin");
        em.persist(a);

	}
	
//	private byte[] hashPasswword(byte[] salt, String password) {
//		try {
//			MessageDigest mg = MessageDigest.getInstance("SHA-256");
//			mg.update(salt);
//			byte[] hashed=mg.digest(password.getBytes());
//			
//			return hashed;
//
//		} catch (Exception e) {
//			return null;
//		}
//	}
//	
//	private byte[] getSalt() {
//		byte[] salt = new byte[16];
//		sm.nextBytes(salt);
//		
//		return salt;
//	}

}
