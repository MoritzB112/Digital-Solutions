package es.uma.proyecto.ejb;

import java.security.SecureRandom;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.proyecto.jpa.Autorizacion;
import es.uma.proyecto.jpa.Autorizacion_PK;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Persona_Autorizada;
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
        
        Persona_Autorizada pa = new Persona_Autorizada();
        pa.setID(1L);
        pa.setNombre("alguno");
        pa.setApellidos("garcia");
        pa.setDireccion("pozo");
        pa.setCiudad("malaga");
        pa.setCodigoPostal(29010);
        pa.setUs(u);
        pa.setPais("España");
        em.persist(pa);
        
        Empresa emp = new Empresa();
        emp.setIdentificacion("algunString");
        emp.setId(1L);
        emp.setTipo_cliente("juridica");
        emp.setEstado("activa");
        emp.setFecha_alta(new Date());
        emp.setDireccion("direccion");
        emp.setCiudad("granada");
        emp.setCodigoPostal(29000);
        emp.setPais("España");
        emp.setRazon_social("trabajador");
        em.persist(emp);
        
        Autorizacion au = new Autorizacion();
        Autorizacion_PK aupk = new Autorizacion_PK();
        aupk.setEmID(1L);
        aupk.setPaID(1L);
        au.setId(aupk);
        au.setTipo("prueba");
        au.setEm(emp);
        au.setPa(pa);
        em.persist(au);
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
