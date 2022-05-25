package es.uma.proyecto.war;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.ejb.GestionClientes;
import es.uma.proyecto.ejb.GestionCuentas;
import es.uma.proyecto.ejb.GestionPersonas_Autorizadas;
import es.uma.proyecto.ejb.GestionUsuarios;
import es.uma.proyecto.ejb.Excepciones.AutorizacionYaExisteException;
import es.uma.proyecto.ejb.Excepciones.ClienteExistenteException;
import es.uma.proyecto.ejb.Excepciones.ClienteNoExisteException;
import es.uma.proyecto.ejb.Excepciones.ClienteNoSuporteadoException;
import es.uma.proyecto.ejb.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.CuentaNoSuporteadaException;
import es.uma.proyecto.ejb.Excepciones.CuentaReferenciaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.CuentaYaExisteException;
import es.uma.proyecto.ejb.Excepciones.PasswordException;
import es.uma.proyecto.ejb.Excepciones.Persona_AutorizadaNoEncontradaException;
import es.uma.proyecto.ejb.Excepciones.Persona_AutorizadaYaExisteException;
import es.uma.proyecto.ejb.Excepciones.SaldoNoVacioException;
import es.uma.proyecto.ejb.Excepciones.TieneCuentaAsociadoException;
import es.uma.proyecto.ejb.Excepciones.UsuarioExistenteException;
import es.uma.proyecto.ejb.Excepciones.UsuarioNoEncontradoException;
import es.uma.proyecto.jpa.Autorizacion;
import es.uma.proyecto.jpa.Cliente;
import es.uma.proyecto.jpa.Cuenta_Fintech;
import es.uma.proyecto.jpa.Cuenta_Referencia;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Individual;
import es.uma.proyecto.jpa.Persona_Autorizada;
import es.uma.proyecto.jpa.Pooled_Account;
import es.uma.proyecto.jpa.Segregada;
import es.uma.proyecto.jpa.Usuario;


@Named(value = "administrativo")
@RequestScoped
public class Admin {
	
    @Inject
	private GestionUsuarios us;

    @Inject
    private GestionClientes gcli;
    
    @Inject
    private GestionCuentas gc;
    
    @Inject
    private GestionPersonas_Autorizadas gpaut;
    

    private Segregada seg;
    private Persona_Autorizada paut;
    private Pooled_Account pa;
	private Usuario usuario;
	private Cliente clin;
	private Individual ind;
	private Empresa em;
	private Autorizacion a;
	//private Cuenta_Referencia cre;

	private String tipo;
	
	public Admin() {
		usuario=new Usuario();
		ind=new Individual();
		em=new Empresa();
		pa=new Pooled_Account();
		seg=new Segregada();
		paut=new Persona_Autorizada();
		a=new Autorizacion();
		clin=new Cliente();
	
		
		tipo=null;
	}
	
//getters, setters
	

	public Cliente getClin() {
		return clin;
	}

	public void setClin(Cliente clin) {
		this.clin = clin;
	}

	public Segregada getSeg() {
		return seg;
	}

	public void setSeg(Segregada seg) {
		this.seg = seg;
	}

	public Persona_Autorizada getPaut() {
		return paut;
	}

	public void setPaut(Persona_Autorizada paut) {
		this.paut = paut;
	}

	public Pooled_Account getPa() {
		return pa;
	}

	public void setPa(Pooled_Account pa) {
		this.pa = pa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Individual getInd() {
		return ind;
	}

	public void setInd(Individual ind) {
		this.ind = ind;
	}

	public Empresa getEm() {
		return em;
	}

	public void setEm(Empresa em) {
		this.em = em;
	}

	public Autorizacion getA() {
		return a;
	}

	public void setA(Autorizacion a) {
		this.a = a;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

//---------------------------------------------------------------------
//Usuario y cliente
	
	public String tipoUsuario() {
		if(tipo.equalsIgnoreCase("Individual")) {
			
			
			return "darAltaIndividual.xhtml";
			
		}else if(tipo.equalsIgnoreCase("Juridica")) {
			
			
			return "darAltaEmpresa.xhtml";
			
		}else if(tipo.equalsIgnoreCase("Persona autorizada")) {
			
			
			return "darAltaPersona_Autorizada.xhtml";
			
			
		}else if(tipo.equalsIgnoreCase("Administrativo")) {	
			
			return "darAltaAdministrativo.xhtml";
		}
		
		return null;
	}
	
	
	public String crearEmpresa() {
		
		try {
			
				em.setFecha_alta(new Date());
				
			if(em.getEstado().equalsIgnoreCase("baja")) {
				em.setFecha_baja(new Date());
			}
				
		gcli.darDeAltaEmpresa(em);
		}catch(ClienteExistenteException ce) {
			
		}
		return "administrativo.xhtml";
	}
	
	
	public String crearIndividual() {
		
		try {
			usuario.setEsAdministrativo(false);
			us.crearUsuario(usuario);
			ind.setId(null);
			ind.setFecha_alta(new Date());
			
			if(ind.getEstado().equalsIgnoreCase("baja")) {
				ind.setFecha_baja(new Date());
			}
			
		gcli.darDeAltaIndividual(usuario, ind);
		}catch(UsuarioExistenteException ue) {
			FacesMessage fm = new FacesMessage("Este usuario ya existe");
			FacesContext.getCurrentInstance().addMessage("formulario_darAltaIndividual:e_darAltaIndividual", fm);
			
	    }catch(PasswordException pe) {
	    	FacesMessage fm = new FacesMessage("Problema con la contraseña");
			FacesContext.getCurrentInstance().addMessage("formulario_darAltaIndividual:e_darAltaIndividual", fm);

	    }catch(ClienteExistenteException ce) {
	    	FacesMessage fm = new FacesMessage("Este cliente ya existe");
			FacesContext.getCurrentInstance().addMessage("formulario_darAltaIndividual:e2_darAltaIndividual", fm);
	    }
		return "administrativo.xhtml";
	}
	
	public String crearAdministrativo() {
		
		try {
			usuario.setEsAdministrativo(true);
			us.crearUsuario(usuario);
			
		}catch(UsuarioExistenteException ue) {

	    }catch(PasswordException pe) {
			
		}
		return "administrativo.xhtml";
	}
	
	
	
	public Individual sacarInd(Long id) {
	return gcli.gtIndividual(id);
		}
	
	public Empresa sacarEmpresa(Long id) {
	return gcli.gtEmpresa(id);
		}
	
	public String darDeBajaInd(Individual i) {
		try {
			gcli.darDeBajaIndividual(i);
		}catch(ClienteNoExisteException e) {
			
		}catch(TieneCuentaAsociadoException te) {
			
			}
		return "administrativo.xhtml";
	}
		
	public String darDeBajaEmp(Empresa e) {
		try {
			
			gcli.darDeBajaEmpresa(e);
		}catch(ClienteNoExisteException ce) {
				
		}catch(TieneCuentaAsociadoException te){
				
		}
		return "administrativo.xhtml";
	}
		
	public List<Individual> gtIndividualNoBloq() {
		List<Individual> i=new ArrayList<>();
		List<Individual> l=gcli.sacarIndividual();
		for(Individual ind:l) {
			if(ind.getEstado().equalsIgnoreCase("alta")) {
				i.add(ind);
			}
		}
		return i;
	}
	
	public List<Empresa> gtEmpresaNoBloq() {
		List<Empresa> i=new ArrayList<>();
		List<Empresa> l=gcli.sacarEmpresas();
		for(Empresa ind:l) {
			if(ind.getEstado().equalsIgnoreCase("alta")) {
				i.add(ind);
			}
		}
		return i;
	}
	
	public List<Individual> gtIndividualBloq() {
		List<Individual> i=new ArrayList<>();
		List<Individual> l=gcli.sacarIndividual();
		for(Individual ind:l) {
			if(ind.getEstado().equalsIgnoreCase("bloqueado")) {
				i.add(ind);
	}
		}
		return i;
		}
	
	public List<Empresa> gtEmpresaBloq() {
		List<Empresa> i=new ArrayList<>();
		List<Empresa> l=gcli.sacarEmpresas();
		for(Empresa ind:l) {
			if(ind.getEstado().equalsIgnoreCase("bloqueado")) {
				i.add(ind);
			}
		}
		return i;
	}
	
	
	public List<Individual> gtIndividual_alta_bloq() {
		List<Individual> i=new ArrayList<>();
		List<Individual> l=gcli.sacarIndividual();
		for(Individual ind:l) {
			if(ind.getEstado().equalsIgnoreCase("alta") || ind.getEstado().equalsIgnoreCase("bloqueado")) {
				i.add(ind);
			}
		}
		return i;
	}
	
	public List<Empresa> gtEmpresa_alta_bloq() {
		List<Empresa> i=new ArrayList<>();
		List<Empresa> l=gcli.sacarEmpresas();
		for(Empresa ind:l) {
			if(ind.getEstado().equalsIgnoreCase("alta") || ind.getEstado().equalsIgnoreCase("bloqueado")) {
				i.add(ind);
			}
		}
		return i;
	}
	
	public  void desbloquear(Cliente cl) {
		try {
		gcli.desbloquearCliente(cl);
		}catch(ClienteNoExisteException ce) {
			
		}catch(ClienteNoSuporteadoException cs) {
			
		}
	}
	
	public  void bloquear(Cliente cl) {
		try {
		gcli.bloquearCliente(cl);
		}catch(ClienteNoExisteException ce) {
			
		}catch(ClienteNoSuporteadoException cs) {
			
		}
	}
	
	public List<Cliente> sacarCli(){
		return gcli.sacarClientes();
	}
	
//---------------------------------------------------------------------------------
//Cuentas
	
	public String crearCuentaSegregada() {
		try {
		
		seg.setFecha_apertura(new Date());
		
		if(seg.getEstado().equalsIgnoreCase("baja")) {
			seg.setFecha_cierre(new Date());
		}
		
		gc.addCuenta(seg, seg.getCr(), seg.getCl());
		
		}catch(CuentaYaExisteException ce) {
			
		}catch(CuentaNoSuporteadaException cs) {
			
		}catch(ClienteNoExisteException cne) {
			
		}catch(CuentaReferenciaNoExisteException cre) {
			
		}
		
		return "administrativo.xhtml";
	}
	
	public String crearCuentaPooled() {
		try {
			
			seg.setFecha_apertura(new Date());
			
			if(pa.getEstado().equalsIgnoreCase("baja")) {
				seg.setFecha_cierre(new Date());
			}
			gc.addCuentaPooled(pa, clin);
			
			}catch(CuentaYaExisteException ce) {
				
			}catch(ClienteNoExisteException cne) {
				
			}
			
			return "administrativo.xhtml";
	}
	
//	public List<Segregada> sacarSeg() {
//		return gc.sacarSegregadas();
//			}
//	
//	public List<Pooled_Account> sacarPA() {
//		return gc.sacarPooledAccount();
//			}
	
	public List<Cuenta_Referencia> sacarCuRef() {
		return gc.sacarCuentaReferencia();
	}
	
	public List<Segregada> gtSegregada_alta_bloq() {
		List<Segregada> i=new ArrayList<>();
		List<Segregada> l=gc.sacarSegregadas();
		for(Segregada ind:l) {
			if(ind.getEstado().equalsIgnoreCase("alta")) {
				i.add(ind);
			}
		}
		return i;
		}
	
	public List<Pooled_Account> gtPooled_alta_bloq() {
		List<Pooled_Account> i=new ArrayList<>();
		List<Pooled_Account> l=gc.sacarPooledAccount();
		for(Pooled_Account ind:l) {
			if(ind.getEstado().equalsIgnoreCase("alta")) {
				i.add(ind);
			}
		}
		return i;
		}
	
	public Segregada sacarSegr(Long id) {
		return gc.gtSegregada(id);
			}
	
	public Pooled_Account sacarPoo(Long id) {
		return gc.gtPooled(id);
			}
	
	public String darDeBajaSeg(Segregada e) {
		try {
			
			gc.cerrarCuenta(e);;
			
		}catch(CuentaNoExisteException ce) {
				
		}catch(SaldoNoVacioException sv){
				
		}catch(CuentaNoSuporteadaException cs) {
			
		}
		
		return "administrativo.xhtml";
	}
	
	public String darDeBajaPool(Pooled_Account e) {
		try {
			
			gc.cerrarCuenta(e);;
			
		}catch(CuentaNoExisteException ce) {
				
		}catch(SaldoNoVacioException sv){
				
		}catch(CuentaNoSuporteadaException cs) {
			
		}
		
		return "administrativo.xhtml";
	}
	
//---------------------------------------
//autorizadas
	
	public String crearPAut() {
		
		try {
			usuario.setEsAdministrativo(false);
			us.crearUsuario(usuario);
			paut.setFechaInicio(new Date());
			
			if(paut.getEstado().equalsIgnoreCase("baja")) {
				paut.setFechaFin(new Date());
			}
			
		gpaut.insertarPersonaAutorizada(usuario, paut);
		
		}catch(UsuarioExistenteException ue) {

	    }catch(PasswordException pe) {

	    }catch(Persona_AutorizadaYaExisteException pye) {
			
		}catch(UsuarioNoEncontradoException une) {
			
		}
		return "administrativo.xhtml";
	}
	
	
	public Persona_Autorizada sacarPaut(Long id) {
	return gpaut.gtPautorizada(id);
		}
	
	public String darDeBajaPA(Persona_Autorizada e) {
		try {
			
			gpaut.eliminarAutorizadoCuenta(e);
			
		}catch(Persona_AutorizadaNoEncontradaException pn) {
				
		}
		return "administrativo.xhtml";
	}
	
	public List<Persona_Autorizada> gtPaut_alta_bloq() {
		List<Persona_Autorizada> i=new ArrayList<>();
		List<Persona_Autorizada> l=gpaut.sacarPA();
		for(Persona_Autorizada ind:l) {
			if(ind.getEstado().equalsIgnoreCase("alta") || ind.getEstado().equalsIgnoreCase("bloqueado")) {
				i.add(ind);
			}
		}
		return i;
		}
	
	public List<Persona_Autorizada> gtPAutBloq() {
		List<Persona_Autorizada> i=new ArrayList<>();
		List<Persona_Autorizada> l=gpaut.sacarPA();
		for(Persona_Autorizada ind:l) {
			if(ind.getEstado().equalsIgnoreCase("bloqueado")) {
				i.add(ind);
			}
		}
		return i;
	}
	
	public List<Persona_Autorizada> gtPautNoBloq() {
		List<Persona_Autorizada> i=new ArrayList<>();
		List<Persona_Autorizada> l=gpaut.sacarPA();
		for(Persona_Autorizada ind:l) {
			if(ind.getEstado().equalsIgnoreCase("alta")) {
				i.add(ind);
			}
		}
		return i;
	}
	
	
	public  void desbloquearPAut(Persona_Autorizada p) {
		try {
		gpaut.desbloquearAutorizado(p);
		
		}catch(Persona_AutorizadaNoEncontradaException ne) {
			
		}
	}
	
	public  void bloquearPAut(Persona_Autorizada p) {
		try {
		gpaut.desbloquearAutorizado(p);
		
		}catch(Persona_AutorizadaNoEncontradaException ne) {
			
		}
	}
	
	public String añadirAutorizacionA() {
		
		try{
		
		gpaut.darAutorizacion(em, a, paut);
		
		}catch(AutorizacionYaExisteException aye) {
			
		}catch(CuentaNoExisteException cne) {
			
		}catch(Persona_AutorizadaYaExisteException pae) {
			
		}
		
		return "administrativo.xhtml";
	}
	
	
	
		


}