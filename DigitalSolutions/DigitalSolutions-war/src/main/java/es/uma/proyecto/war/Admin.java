package es.uma.proyecto.war;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;

import es.uma.proyecto.ejb.GestionClientes;
import es.uma.proyecto.ejb.GestionCuentas;
import es.uma.proyecto.ejb.GestionGenerarReportes;
import es.uma.proyecto.ejb.GestionPersonas_Autorizadas;
import es.uma.proyecto.ejb.GestionUsuarios;
import es.uma.proyecto.ejb.Excepciones.AutorizacionNoExisteException;
import es.uma.proyecto.ejb.Excepciones.AutorizacionYaExisteException;
import es.uma.proyecto.ejb.Excepciones.ClienteExistenteException;
import es.uma.proyecto.ejb.Excepciones.ClienteNoExisteException;
import es.uma.proyecto.ejb.Excepciones.ClienteNoSuporteadoException;
import es.uma.proyecto.ejb.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.CuentaNoSuporteadaException;
import es.uma.proyecto.ejb.Excepciones.CuentaReferenciaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.CuentaYaExisteException;
import es.uma.proyecto.ejb.Excepciones.Depositado_enNoExisteException;
import es.uma.proyecto.ejb.Excepciones.PasswordException;
import es.uma.proyecto.ejb.Excepciones.Persona_AutorizadaNoEncontradaException;
import es.uma.proyecto.ejb.Excepciones.Persona_AutorizadaYaExisteException;
import es.uma.proyecto.ejb.Excepciones.SaldoNoVacioException;
import es.uma.proyecto.ejb.Excepciones.TieneCuentaAsociadoException;
import es.uma.proyecto.ejb.Excepciones.UsuarioExistenteException;
import es.uma.proyecto.ejb.Excepciones.UsuarioNoEncontradoException;
import es.uma.proyecto.jpa.Autorizacion;
import es.uma.proyecto.jpa.Cliente;
import es.uma.proyecto.jpa.Cuenta_Referencia;
import es.uma.proyecto.jpa.Depositado_en;
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
    
    @Inject
    private GestionGenerarReportes greportes;
    

    private Segregada seg;
    private Persona_Autorizada paut;
    private Pooled_Account pa;
	private Usuario usuario;
	private Cliente clin;
	private Individual ind;
	private Empresa em;
	private Autorizacion a;
	private Long id;
	private String iban;
	private Long id2;
	private String iban2;
	
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
		id=null;
		id2=null;
		iban=null;
		iban2=null;
	
	
		
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
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}
	

	public Long getId2() {
		return id2;
	}

	public void setId2(Long id2) {
		this.id2 = id2;
	}

	public String getIban2() {
		return iban2;
	}

	public void setIban2(String iban2) {
		this.iban2 = iban2;
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
			FacesMessage fm = new FacesMessage("Este cliente ya existe");
			FacesContext.getCurrentInstance().addMessage("formularioEmpresa_darAlta:e_darAltaEmpresa", fm);		
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
		return "administrativo.xhtml";
		
		}catch(UsuarioExistenteException ue) {
			FacesMessage fm = new FacesMessage("Este usuario ya existe");
			FacesContext.getCurrentInstance().addMessage("formulario_modificar_individual:e_modificarIndividual", fm);
			
	    }catch(PasswordException pe) {
	    	FacesMessage fm = new FacesMessage("Problema con la contraseña");
			FacesContext.getCurrentInstance().addMessage("formulario_darAltaIndividual:e_modificarIndividual", fm);

	    }catch(ClienteExistenteException ce) {
	    	FacesMessage fm = new FacesMessage("Este cliente ya existe");
			FacesContext.getCurrentInstance().addMessage("formulario_darAltaIndividual:e2_modificarIndividual", fm);
			
	    } catch (UsuarioNoEncontradoException e) {
	    	FacesMessage fm = new FacesMessage("Usuario no encontrado");
			FacesContext.getCurrentInstance().addMessage("formulario_darAltaIndividual:e_modificarIndividual", fm);
		}
		return null;
	}
	
	public String crearAdministrativo() {
		
		try {
			usuario.setEsAdministrativo(true);
			us.crearUsuario(usuario);
			return "administrativo.xhtml";
		}catch(UsuarioExistenteException ue) {
			FacesMessage fm = new FacesMessage("Este usuario ya existe");
			FacesContext.getCurrentInstance().addMessage("formularioAdministrativo_darDeAlta:e_darAltaAdministrativo", fm);	

	    }catch(PasswordException pe) {
	    	FacesMessage fm = new FacesMessage("Problema con la contraseña");
			FacesContext.getCurrentInstance().addMessage("formularioAdministrativo_darDeAlta:e_darAltaAdministrativo", fm);	
			
		}
		return null;
	}
	
	
	
	public Individual sacarInd(Long id) {
	return gcli.gtIndividual(id);
		}
	
	public Empresa sacarEmpresa(Long id) {
	return gcli.gtEmpresa(id);
		}
	
	
	public String darDeBajaInd(Individual in) {
		try {
			gcli.darDeBajaIndividual(in);
			return "administrativo.xhtml?faces-redirect=true";
		}catch(ClienteNoExisteException e) {
			
			FacesMessage fm = new FacesMessage("Cliente no existe");
			FacesContext.getCurrentInstance().addMessage("formularioUsuario_darDeBaja:e_darDeBajaInd", fm);
			
		}catch(TieneCuentaAsociadoException te) {
			
			FacesMessage fm = new FacesMessage("Tiene cuenta ya asociada");
			FacesContext.getCurrentInstance().addMessage("formularioUsuario_darDeBaja:e_darDeBajaInd", fm);
			
			}
		return null;
	}
		
	public String darDeBajaEmp(Empresa e) {
		try {
			
			gcli.darDeBajaEmpresa(e);
			return "administrativo.xhtml?faces-redirect=true";
		}catch(ClienteNoExisteException ce) {
			
			FacesMessage fm = new FacesMessage("Cliente no existe");
			FacesContext.getCurrentInstance().addMessage("formularioUsuario_darDeBaja:e_darDeBajaEmp", fm);
				
		}catch(TieneCuentaAsociadoException te){
			
			FacesMessage fm = new FacesMessage("Tiene cuenta ya asociada");
			FacesContext.getCurrentInstance().addMessage("formularioUsuario_darDeBaja:e_darDeBajaEmp", fm);
				
		}
		return null;
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
			FacesMessage fm = new FacesMessage("Cliente no existe");
			FacesContext.getCurrentInstance().addMessage("formulario_desbloquear_usuarios:e_desbloquear_usuario", fm);
			
		}catch(ClienteNoSuporteadoException cs) {
			FacesMessage fm = new FacesMessage("Cliente no suporteado");
			FacesContext.getCurrentInstance().addMessage("formulario_desbloquear_usuarios:e_desbloquear_usuario", fm);
			
		}
	}
	
	public  void bloquear(Cliente cl) {
		try {
		gcli.bloquearCliente(cl);
		}catch(ClienteNoExisteException ce) {
			FacesMessage fm = new FacesMessage("Cliente no existe");
			FacesContext.getCurrentInstance().addMessage("formulario_bloquear_usuarios:e_bloquear_usuario", fm);
			
		}catch(ClienteNoSuporteadoException cs) {
			FacesMessage fm = new FacesMessage("Cliente no suporteado");
			FacesContext.getCurrentInstance().addMessage("formulario_bloquear_usuarios:e_bloquear_usuario", fm);
		}
	}
	
	public List<Cliente> sacarCli(){
		List<Cliente> i=new ArrayList<>();
		List<Cliente> l=gcli.sacarClientes();
		for(Cliente ind:l) {
			if(ind.getEstado().equalsIgnoreCase("alta") || ind.getEstado().equalsIgnoreCase("bloqueado")) {
				i.add(ind);
			}
		}
		return i;
		}

	
//---------------------------------------------------------------------------------
//Cuentas
	
	public String crearCuentaSegregada() {
		try {
		
			Cuenta_Referencia cr=new Cuenta_Referencia();
			Cliente cl=new Cliente();
			cr.setIBAN(iban);			
			cl.setId(id);
			
		seg.setFecha_apertura(new Date());
		
		if(seg.getEstado().equalsIgnoreCase("baja")) {
			seg.setFecha_cierre(new Date());
		}
		
		gc.addCuenta(seg, cr, cl);
		
		return "administrativo.xhtml";
		
		}catch(CuentaYaExisteException ce) {
			FacesMessage fm = new FacesMessage("Esta cuenta ya existe");
			FacesContext.getCurrentInstance().addMessage("formulario_abrirCuentaSegregada:e_abrirCuentaSegregada", fm);
			
		}catch(CuentaNoSuporteadaException cs) {
			FacesMessage fm = new FacesMessage("Cuenta no suporteada");
			FacesContext.getCurrentInstance().addMessage("formulario_abrirCuentaSegregada:e_abrirCuentaSegregada", fm);
			
		}catch(ClienteNoExisteException cne) {
			FacesMessage fm = new FacesMessage("Este cliente no existe");
			FacesContext.getCurrentInstance().addMessage("formulario_abrirCuentaSegregada:e_abrirCuentaSegregada", fm);
			
		}catch(CuentaReferenciaNoExisteException cre) {
			FacesMessage fm = new FacesMessage("Esta cuenta de referencia no existe");
			FacesContext.getCurrentInstance().addMessage("formulario_abrirCuentaSegregada:e_abrirCuentaSegregada", fm);
			
		}
		
		return null;
	}
	
	public String crearCuentaPooled() {
		try {
			
			Cliente cl=new Cliente();		
			cl.setId(id);
			
			pa.setFecha_apertura(new Date());
			
			if(pa.getEstado().equalsIgnoreCase("baja")) {
				pa.setFecha_cierre(new Date());
			}
			gc.addCuentaPooled(pa, cl);
			
			return "administrativo.xhtml";
			
			}catch(CuentaYaExisteException ce) {
				FacesMessage fm = new FacesMessage("Esta cuenta de referencia no existe");
				FacesContext.getCurrentInstance().addMessage("formulario_abrirCuentaPooled:e_abrirCuentaPooled", fm);
				
			}catch(ClienteNoExisteException cne) {
				FacesMessage fm = new FacesMessage("Esta cuenta de referencia no existe");
				FacesContext.getCurrentInstance().addMessage("formulario_abrirCuentaPooled:e_abrirCuentaPooled", fm);
				
			}
			
			return null;
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
			if(ind.getEstado().equalsIgnoreCase("alta") || ind.getEstado().equalsIgnoreCase("bloqueado")) {
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
	
	public List<Cuenta_Referencia> gtCuentaReferencia_alta() {
		List<Cuenta_Referencia> i=new ArrayList<>();
		List<Cuenta_Referencia> l=gc.sacarCuentaReferencia();
		for(Cuenta_Referencia ind:l) {
			if(ind.getEstado().equalsIgnoreCase("alta")) {
				i.add(ind);
			}
		}
		return i;
		}
	
	
	
	
	public Segregada sacarSegr(String id) {
		return gc.gtSegregada(id);
			}
	
	public Pooled_Account sacarPoo(String id) {
		return gc.gtPooled(id);
			}
	
	public String darDeBajaSeg(Segregada e) {
		try {
			
			gc.cerrarCuenta(e);
			
			return "administrativo.xhtml?faces-redirect=true";
			
		}catch(CuentaNoExisteException ce) {
			FacesMessage fm = new FacesMessage("Esta cuenta no existe");
			FacesContext.getCurrentInstance().addMessage("formularioCuenta_darDeBaja:e_darDeBajaCuentaSegregada", fm);	
				
		}catch(SaldoNoVacioException sv){
			FacesMessage fm = new FacesMessage("Saldo no vacío");
			FacesContext.getCurrentInstance().addMessage("formularioCuenta_darDeBaja:e_darDeBajaCuentaSegregada", fm);	
				
		}catch(CuentaNoSuporteadaException cs) {
			FacesMessage fm = new FacesMessage("Cuenta no suporteada");
			FacesContext.getCurrentInstance().addMessage("formularioCuenta_darDeBaja:e_darDeBajaCuentaSegregada", fm);	
			
		}
		
		return null;
	}
	
	public String darDeBajaPool(Pooled_Account e) {
		try {
			
			gc.cerrarCuenta(e);
			
			return "administrativo.xhtml?faces-redirect=true";
			
		}catch(CuentaNoExisteException ce) {
			FacesMessage fm = new FacesMessage("Este cliente ya existe");
			FacesContext.getCurrentInstance().addMessage("formularioCuenta_darDeBaja:e_darDeBajaCuentaPooled", fm);	
				
		}catch(SaldoNoVacioException sv){
			FacesMessage fm = new FacesMessage("Saldo no vacío");
			FacesContext.getCurrentInstance().addMessage("formularioCuenta_darDeBaja:e_darDeBajaCuentaPooled", fm);	
				
		}catch(CuentaNoSuporteadaException cs) {
			FacesMessage fm = new FacesMessage("Cuenta no suporteada");
			FacesContext.getCurrentInstance().addMessage("formularioCuenta_darDeBaja:e_darDeBajaCuentaPooled", fm);	
			
		}
		
		return null;
	}
	
	
	public String agregarCartera() {
		
		try {
			Pooled_Account p=new Pooled_Account();
			p.setIBAN(iban);
			
			Cuenta_Referencia cr=new Cuenta_Referencia();
			cr.setIBAN(iban2);
			gc.añadirCartera(p, cr);
			return "administrativo.xhtml";
		} catch (CuentaNoExisteException e) {
			FacesMessage fm = new FacesMessage("Cuenta no suporteada");
			FacesContext.getCurrentInstance().addMessage("form_agregar_cartera:e_agregarCartera", fm);
		}
		return null;
	}
	
	public List<Depositado_en> gtCarteras() {
		List<Depositado_en> i=new ArrayList<>();
		List<Depositado_en> l=gc.sacarCarteras();
		for(Depositado_en ind:l) {
			if(!ind.getPa().getEstado().equalsIgnoreCase("baja")) {
				i.add(ind);
			}
		}
		return i;
		}
	
	public String eliminarCartera(Depositado_en de) {
		try {
			gc.eliminarCartera(de);
			return "administrativo.xhtml";
			
		} catch (Depositado_enNoExisteException e) {
			FacesMessage fm = new FacesMessage("Esta cartera no existe");
			FacesContext.getCurrentInstance().addMessage("form_eliminar_cartera:e_eliminarCartera", fm);
		}
		return null;
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
		return "administrativo.xhtml";
		
		}catch(UsuarioExistenteException ue) {
			FacesMessage fm = new FacesMessage("Este usuario ya existe");
			FacesContext.getCurrentInstance().addMessage("formularioPA_darDeAlta:e_darAltaPersona_Autorizada", fm);	

	    }catch(PasswordException pe) {
			FacesMessage fm = new FacesMessage("Problema con la contraseña");
			FacesContext.getCurrentInstance().addMessage("formularioPA_darDeAlta:e_darAltaPersona_Autorizada", fm);	

	    }catch(Persona_AutorizadaYaExisteException pye) {
			FacesMessage fm = new FacesMessage("Esta persona autorizada ya existe");
			FacesContext.getCurrentInstance().addMessage("formularioPA_darDeAlta:e2_darAltaPersona_Autorizada", fm);	
			
		}catch(UsuarioNoEncontradoException une) {
			FacesMessage fm = new FacesMessage("Usuario no encontrado");
			FacesContext.getCurrentInstance().addMessage("formularioPA_darDeAlta:e_darAltaPersona_Autorizada", fm);	
			
		}
		return null;
	}
	
	
	public Persona_Autorizada sacarPaut(Long id) {
	return gpaut.gtPautorizada(id);
		}
	
	public String darDeBajaPA(Persona_Autorizada e) {
		try {
			
			gpaut.eliminarAutorizadoCuenta(e);
			return "administrativo.xhtml?faces-redirect=true";
			
		}catch(Persona_AutorizadaNoEncontradaException pn) {
			
			FacesMessage fm = new FacesMessage("Persona autorizada no encontrada");
			FacesContext.getCurrentInstance().addMessage("formularioUsuario_darDeBaja:e_darDeBajaPAut", fm);	
		}
		return null;
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
			FacesMessage fm = new FacesMessage("Persona autorizada no encontrada");
			FacesContext.getCurrentInstance().addMessage("formulario_desbloquear_usuarios:e_desbloquear_usuario", fm);	
		}
	}
	
	public  void bloquearPAut(Persona_Autorizada p) {
		try {
		gpaut.bloquearAutorizado(p);
		
		}catch(Persona_AutorizadaNoEncontradaException ne) {
			FacesMessage fm = new FacesMessage("Persona autorizada no encontrada");
			FacesContext.getCurrentInstance().addMessage("formulario_bloquear_usuario:e_bloquearUsuario", fm);	
		}
	}
	
	public String añadirAutorizacionA() {
		
		try{
			
			Empresa p=new Empresa();
			p.setId(id);
			Persona_Autorizada autorizado=new Persona_Autorizada();
			autorizado.setID(id2);
		
		gpaut.darAutorizacion(p, a, autorizado);
		return "administrativo.xhtml";
		
		}catch(AutorizacionYaExisteException aye) {
			FacesMessage fm = new FacesMessage("Autorización ya existe");
			FacesContext.getCurrentInstance().addMessage("form_modificar_lista_cuentas:e_anadirAutorizacion", fm);	
			
		}catch(CuentaNoExisteException cne) {
			FacesMessage fm = new FacesMessage("Esta cuenta no existe");
			FacesContext.getCurrentInstance().addMessage("form_modificar_lista_cuentas:e_anadirAutorizacion", fm);	
			
		}catch(Persona_AutorizadaYaExisteException pae) {
			FacesMessage fm = new FacesMessage("Esta persona autorizada ya existe");
			FacesContext.getCurrentInstance().addMessage("form_modificar_lista_cuentas:e_anadirAutorizacion", fm);	
			
		}
		
		return null;
	}
	
	public List<Autorizacion> gtAutorizaciones() {
		List<Autorizacion> l=gpaut.sacarAutorizaciones();
		return l;
	}
	
	public String eliminarAutorizacion(Autorizacion au) {
		try {
			gpaut.eliminarAutorizacion(au);
			return "administrativo.xhtml?faces-redirect=true";
		} catch (AutorizacionNoExisteException e) {
			FacesMessage fm = new FacesMessage("Esta autorización no existe");
			FacesContext.getCurrentInstance().addMessage("form_eliminar_autorizacion:e_eliminarAutorizacion", fm);	
		}
		
		return null;
	}
	
	
	
//---------------------------------------
//Informes
	
	public void download() {
		File file;
		try {
			file = new File(greportes.generarReportePrimero());
			  Faces.sendFile(file, true);
			  
		} catch (IOException e) {
			
		}
	  
	}
	
	public void download2() {
		File file;
		try {
			file = new File(greportes.generarReporteSegundo());
			  Faces.sendFile(file, true);
			  
		} catch (IOException e) {
			
		}
	  
	}
	
//-----------------------------------------------------------------
	
	public String sacarEmpr(Long id) {
		return gcli.gtEmpresa(id).getIdentificacion();
	}
	
	public String sacarCli(Long id) {
		Empresa em=gcli.gtEmpresa(id);
		if(em!=null) {
			return em.getIdentificacion();
		}
		Individual ind=gcli.gtIndividual(id);
		if(ind!=null) {
			return ind.getIdentificacion();
		}
		return null;
	}
	
	public String sacarPerAut(Long id) {
		return gpaut.gtPautorizada(id).getIdentificacion();
	}
	
	public String sacarCr(String iban) {
		Cuenta_Referencia cr=gc.gtRef(iban);
		return cr.getIBAN()+"("+cr.getDiv().getAbreviatura()+")";
	}

//-------------------------------
//datatables
	
	public String listaInd(Long id) {
		return "modificar_individual.xhtml?faces-redirect=trueid="+id;
	}
	
	public String listaEmp(Long id) {
		return "modificar_empresa.xhtml?faces-redirect=trueid="+id;
	}
	
	public String listaPAut(Long id) {
		return "modificar_Persona_Autorizada.xhtml?faces-redirect=trueid="+id;
	}
	
	public String listaSeg(String id) {
		return "modificar_segregada.xhtml?faces-redirect=trueiban="+id;
	}
	
	public String listaPool(String id) {
		return "modificar_pooled.xhtml?faces-redirect=trueiban="+id;
	}
	
	public String listaDiv(String id) {
		return "cambioDivisas.xhtml?faces-redirect=trueiban="+id;
	}

		

}