package es.uma.proyecto;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



@Stateless
public class ClientesEJB implements GestionClientes {
	
	@PersistenceContext(name="proyecto-ejb")
	private EntityManager em;
	
/*
	public boolean esAdministrativo(Cliente cl) {
		Cliente c=em.find(Cliente.class, cl.getId());
		if(c==null) {
			throw new RuntimeException();
		}
		
		return c.getTipo_cliente().equalsIgnoreCase("Administrativo");
	}

	@Override
	public Cliente clienteRegistrado(String usuario, String contraseña) {
		List<Cliente> l=em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
		
		for(Cliente c:l) {
			if(usuario.equals(c.getIdentificacion())) {
				if(comprobarContraseña(c,contraseña)) {
					
					return c;
					
				}else {
					
				throw new RuntimeException();
				
				}
			}
		}
		
		throw new RuntimeException();
	}
	

	private boolean comprobarContraseña(Cliente c, String contraseña) {
		try {
		byte[] salt=c.getSalt().getBytes(); 
		ByteArrayOutputStream contra=new ByteArrayOutputStream();
		contra.write(contraseña.getBytes());
		contra.write(salt);
		MessageDigest mg=MessageDigest.getInstance("SHA-256");
		
		return (c.getPassword().equals(new String(mg.digest(contra.toByteArray()))));
		
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void darDeAlta(Cliente cl) {
		if(em.find(Cliente.class, cl.getId())==null) {
			throw new RuntimeException();
		}
		//Generamos la salt
		byte[] salt=new byte[16];
		SecureRandom sm=new SecureRandom();
		sm.nextBytes(salt);
		cl.setSalt(new String(salt));
		
		cl.setPassword(hashPassword(cl.getPassword(), salt));
		
		if(cl instanceof Empresa) {
			Empresa ent=(Empresa) cl;
			ent.setTipo_cliente("JURIDICA");
			em.persist(ent);
			
		}else if(cl instanceof Individual) {
			Individual ind=(Individual) cl;
			ind.setTipo_cliente("FISICA");
			em.persist(ind);
			
		}else {
			cl.setTipo_cliente("Autorizado");
			em.persist(cl);
		}
	}
	
	private String hashPassword(String password, byte[] salt) {
		ByteArrayOutputStream contra=new ByteArrayOutputStream();
		try {
		contra.write(password.getBytes());
		contra.write(salt);
		MessageDigest mg=MessageDigest.getInstance("SHA-256");
		
		return new String (mg.digest(contra.toByteArray()));
		
		}catch (Exception e){
			throw new RuntimeException();
		}
	}

	public void modificarCliente(Cliente cl) {
		
		if(cl instanceof Empresa) {
			Empresa ent=(Empresa) cl;
			
			if(em.find(Empresa.class, ent.getId())==null) {
				throw new RuntimeException();
			}
			em.merge(ent);
			
		}else if(cl instanceof Individual) {
			Individual ind=(Individual) cl;
			if(em.find(Individual.class, ind.getId())==null) {
				throw new RuntimeException();
			}
			em.merge(ind);
			
		}else {
			if(em.find(Cliente.class, cl.getId())==null) {
				throw new RuntimeException();
			}
			em.merge(cl);
		}
	}
	
	public void darDeBaja(Cliente cl) {
		Cliente c=em.find(Cliente.class, cl.getId());
		if(c==null) {
			throw new RuntimeException();
		}
		
		if(!c.getCf().isEmpty()) {
			throw new RuntimeException();
		}
		
		c.setEstado("Baja");
	}
*/
	
}
