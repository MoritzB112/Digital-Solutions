package es.uma.proyecto;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



@Stateless
public class UsuariosEJB implements GestionUsuarios {

public boolean esAdministrativo(Usuario user) {
		Usuario u=em.find(Usuario.class, cl.getId());
		if(c==null) {
			throw new RuntimeException();
		}
		
		return c.getTipo_cliente().equalsIgnoreCase("Administrativo");
	}
}