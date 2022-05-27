package es.uma.proyecto.war;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.ejb.GestionClientes;
import es.uma.proyecto.ejb.GestionPersonas_Autorizadas;
import es.uma.proyecto.ejb.GestionUsuarios;
import es.uma.proyecto.ejb.Excepciones.ClienteNoExisteException;
import es.uma.proyecto.ejb.Excepciones.ClienteNoSuporteadoException;
import es.uma.proyecto.ejb.Excepciones.Persona_AutorizadaNoEncontradaException;
import es.uma.proyecto.ejb.Excepciones.UsuarioNoEncontradoException;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Individual;
import es.uma.proyecto.jpa.Persona_Autorizada;
import es.uma.proyecto.jpa.Usuario;

@Named(value = "modificaciones")
@ViewScoped
public class Modificaciones implements Serializable{
	
	@Inject
	private GestionUsuarios us;

    @Inject
    private GestionClientes gcli;
    
    @Inject
    private GestionPersonas_Autorizadas gpaut;
	
	private Long id;
	private Empresa em;
	private Individual ind;
	private Persona_Autorizada paut;
	private Usuario usPaut;
	private Usuario usInd;
	
	public Modificaciones() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Empresa getEm() {
		if(em==null) {
			em=gcli.gtEmpresa(id);
		}
		return em;
	}

	public void setEm(Empresa em) {
		this.em = em;
	}

	public Individual getInd() {
		if(ind==null) {
			ind=gcli.gtIndividual(id);
		}
		return ind;
	}

	public void setInd(Individual ind) {
		this.ind = ind;
	}

	public Persona_Autorizada getPaut() {
		if(paut==null) {
			paut=gpaut.gtPautorizada(id);
		}
		return paut;
	}

	public void setPaut(Persona_Autorizada paut) {
		this.paut = paut;
	}

	public Usuario getUsPaut() {
		if(usPaut==null) {
			usPaut=getPaut().getUs();
		}
		return usPaut;
	}

	public void setUsPaut(Usuario usPaut) {
		this.usPaut = usPaut;
	}

	public Usuario getUsInd() {
		if(usInd==null) {
			usInd=getInd().getUs();
		}
		return usInd;
	}

	public void setUsInd(Usuario usInd) {
		this.usInd = usInd;
	}
	
//----------------------------------
//Funciones modificar
	
	public String actualizarInd() {
		try {
			
			us.actualizarUsuario(usInd);
			gcli.modificarCliente(ind);
			return "administrativo.xhtml";
			
		} catch (UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("Usuario no encontrado");
			FacesContext.getCurrentInstance().addMessage("formulario_darAltaIndividual:e_darAltaIndividual", fm);
		} catch (ClienteNoExisteException e) {
			FacesMessage fm = new FacesMessage("Cliente no existe");
			FacesContext.getCurrentInstance().addMessage("formulario_darAltaIndividual:e_darAltaIndividual", fm);
		} catch (ClienteNoSuporteadoException e) {
			FacesMessage fm = new FacesMessage("Cliente no suporteado");
			FacesContext.getCurrentInstance().addMessage("formulario_darAltaIndividual:e_darAltaIndividual", fm);
		}
		return null;
	}
	
	public String actualizarEmpresa() {
		try {
			
			gcli.modificarCliente(em);
			return "administrativo.xhtml";
		
		} catch (ClienteNoExisteException e) {
			FacesMessage fm = new FacesMessage("Cliente no existe");
			FacesContext.getCurrentInstance().addMessage("formulario_modificar_empresa:e_modificarEmpresa", fm);
		} catch (ClienteNoSuporteadoException e) {
			FacesMessage fm = new FacesMessage("Cliente no suporteado");
			FacesContext.getCurrentInstance().addMessage("formulario_modificar_empresa:e_modificarEmpresa", fm);
		}
		return null;
	}
	
	
	public String actualizarPAut() {
		try {
			
			us.actualizarUsuario(usPaut);
			gpaut.modificarDatosAutorizado(paut);
			return "administrativo.xhtml";
			
		} catch (UsuarioNoEncontradoException e) {
			FacesMessage fm = new FacesMessage("Usuario no encontrado");
			FacesContext.getCurrentInstance().addMessage("formulario_modificar_Persona_Autorizada:e_modificarPA", fm);
		} catch (Persona_AutorizadaNoEncontradaException e) {
			FacesMessage fm = new FacesMessage("Persona autorizada no encontrada");
			FacesContext.getCurrentInstance().addMessage("formulario_modificar_Persona_Autorizada:e_modificarPA", fm);
		}
		return null;
	}


	
	
	
	
}
