package es.uma.proyecto.war;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.uma.proyecto.ejb.GestionClientes;
import es.uma.proyecto.ejb.GestionCuentas;
import es.uma.proyecto.ejb.GestionPersonas_Autorizadas;
import es.uma.proyecto.ejb.GestionTransacciones;
import es.uma.proyecto.ejb.GestionUsuarios;
import es.uma.proyecto.ejb.Excepciones.ClienteNoExisteException;
import es.uma.proyecto.ejb.Excepciones.ClienteNoSuporteadoException;
import es.uma.proyecto.ejb.Excepciones.CuentaNoExisteException;
import es.uma.proyecto.ejb.Excepciones.CuentaNoSuporteadaException;
import es.uma.proyecto.ejb.Excepciones.CuentasNoIgualesException;
import es.uma.proyecto.ejb.Excepciones.DepositoNoExisteException;
import es.uma.proyecto.ejb.Excepciones.Persona_AutorizadaNoEncontradaException;
import es.uma.proyecto.ejb.Excepciones.SaldoInsuficianteException;
import es.uma.proyecto.ejb.Excepciones.TransaccionYaExisteException;
import es.uma.proyecto.ejb.Excepciones.UsuarioNoEncontradoException;
import es.uma.proyecto.jpa.Cuenta_Referencia;
import es.uma.proyecto.jpa.Depositado_en;
import es.uma.proyecto.jpa.Depositado_en_PK;
import es.uma.proyecto.jpa.Empresa;
import es.uma.proyecto.jpa.Individual;
import es.uma.proyecto.jpa.Persona_Autorizada;
import es.uma.proyecto.jpa.Pooled_Account;
import es.uma.proyecto.jpa.Segregada;
import es.uma.proyecto.jpa.Transaccion;
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
    
    @Inject
    private GestionCuentas gc;
    
    @EJB
	private GestionTransacciones transacciones;
	
	private Long id;
	private String iban;
	private String password;
	private Empresa em;
	private Individual ind;
	private Persona_Autorizada paut;
	private Segregada seg;
	private Pooled_Account pa;
	private Usuario usPaut;
	private Usuario usInd;
	
	private String ibanc;
	private Transaccion tr;
	private String depOr;
	private String depDest;
	
	public Modificaciones() {
		tr=new Transaccion();
		ibanc=null;
		depOr=new String();
		depDest=new String();
		password=new String();
	}
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getIban() {
		return iban;
	}


	public void setIban(String iban) {
		this.iban = iban;
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
	
	public Segregada getSeg() {
		if(seg==null) {
			Segregada aux=new Segregada();
			aux.setIBAN(iban);
			try {
				seg=(Segregada) gc.sacarCuenta(aux);
			} catch (CuentaNoExisteException | CuentaNoSuporteadaException e) {
			}
		}
		return seg;
	}

	public void setSeg(Segregada seg) {
		this.seg = seg;
	}

	public Pooled_Account getPa() {
		if(pa==null) {
			Pooled_Account aux=new Pooled_Account();
			aux.setIBAN(iban);
			try {
				pa=(Pooled_Account) gc.sacarCuenta(aux);
			} catch (CuentaNoExisteException | CuentaNoSuporteadaException e) {
			}
		}
		return pa;
	}

	public void setPa(Pooled_Account pa) {
		this.pa = pa;
	}
	
	public String getIbanc() {
		return ibanc;
	}

	public void setIbanc(String ibanc) {
		sacarPA(ibanc);
		this.ibanc = ibanc;
	}
	
	public String getDepOr() {
		return depOr;
	}

	public void setDepOr(String depOr) {
		this.depOr = depOr;
	}

	public String getDepDest() {
		return depDest;
	}

	public void setDepDest(String depDest) {
		this.depDest = depDest;
	}
	
	public Transaccion getTr() {
		return tr;
	}

	public void setTr(Transaccion tr) {
		this.tr = tr;
	}
	
	
//----------------------------------
//Funciones modificar clientes
	
	

	public String actualizarInd() {
		try {
			
			if(password!=null && !password.equals("")) {
				usInd.setPassword(parseString(hashPassword(parseByte(usInd.getSalt()), password.getBytes())));
			}
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
			
			if(password!=null && !password.equals("")) {
				usPaut.setPassword(parseString(hashPassword(parseByte(usPaut.getSalt()), password.getBytes())));
			}
			
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


	//----------------------------------
	//Funciones modificar cuenta
	
	public String actualizarSegregada() {
		try {
			
			gc.modificarCuenta(seg);
			return "administrativo.xhtml";
			
		} catch (CuentaNoExisteException e) {
			FacesMessage fm = new FacesMessage("Esta cuenta no existe");
			FacesContext.getCurrentInstance().addMessage("form_modificar_segregada:e_modificarSegregada", fm);

		} catch (CuentaNoSuporteadaException e) {
			FacesMessage fm = new FacesMessage("Cuenta no suporteada");
			FacesContext.getCurrentInstance().addMessage("form_modificar_segregada:e_modificarSegregada", fm);
		}
		return null;
	}
	
	public String actualizarPooled() {
	try {
			
			gc.modificarCuenta(pa);
			return "administrativo.xhtml";
			
		} catch (CuentaNoExisteException e) {
			FacesMessage fm = new FacesMessage("Esta cuenta no existe");
			FacesContext.getCurrentInstance().addMessage("form_modificar_pooled:e_modificarPooled", fm);

		} catch (CuentaNoSuporteadaException e) {
			FacesMessage fm = new FacesMessage("Cuenta no suporteada");
			FacesContext.getCurrentInstance().addMessage("form_modificar_pooled:e_modificarPooled", fm);
		}
		return null;
	}
	
	//-----------------------------
	//Divisas
		
		public void sacarPA(String iban) {
			Pooled_Account aux=new Pooled_Account();
			aux.setIBAN(iban);
			
			try {
				pa=(Pooled_Account) gc.sacarCuenta(aux);
			} catch (CuentaNoExisteException | CuentaNoSuporteadaException e) {
			}
		}
		
		public List<Depositado_en> sacarDeps() {
			return pa.getDeps();
			}
		
		public String cambiarDivisa() {
			try {
				tr.setOrigen(pa);
				tr.setDestino(pa);
				tr.setComision(0.0);
				Cuenta_Referencia crOr=new Cuenta_Referencia();
				crOr.setIBAN(depOr.split(";")[1]);
				tr.setDivEm(((Cuenta_Referencia)gc.sacarCuenta(crOr)).getDiv());
				Cuenta_Referencia crDest=new Cuenta_Referencia();
				crDest.setIBAN(depDest.split(";")[1]);
				tr.setDivEm(((Cuenta_Referencia)gc.sacarCuenta(crDest)).getDiv());
				tr.setTipo("Cambio de divisa");
				tr.setFechaInstruccion(new Date());//-------------------
				Depositado_en_PK depOrPK=new Depositado_en_PK();
				depOrPK.setPaID(depOr.split(";")[0]);
				depOrPK.setCrID(depOr.split(";")[1]);
				Depositado_en depOr=new Depositado_en();
				depOr.setId(depOrPK);
				Depositado_en_PK depDestPK=new Depositado_en_PK();
				depDestPK.setPaID(depDest.split(";")[0]);
				depDestPK.setCrID(depDest.split(";")[1]);
				Depositado_en depDest=new Depositado_en();
				depDest.setId(depDestPK);
				transacciones.cambioDivisa(tr, depOr, depDest);
				this.tr=new Transaccion();
				this.depOr=new String();
				this.depDest=new String();
				return "administrativo.xhtml";
			} catch (TransaccionYaExisteException e) {
				FacesMessage fm = new FacesMessage("Esta transacción ya existe");
				FacesContext.getCurrentInstance().addMessage("form_modificar_cambioDivisas:e_cambioDivisas", fm);	
			} catch (DepositoNoExisteException e) {
				FacesMessage fm = new FacesMessage("Este depósito no existe");
				FacesContext.getCurrentInstance().addMessage("form_modificar_cambioDivisas:e_cambioDivisas", fm);	
			} catch (CuentasNoIgualesException e) {
				FacesMessage fm = new FacesMessage("La cuentas no son iguales");
				FacesContext.getCurrentInstance().addMessage("form_modificar_cambioDivisas:e_cambioDivisas", fm);	
			} catch (SaldoInsuficianteException e) {
				FacesMessage fm = new FacesMessage("Saldo insuficiente");
				FacesContext.getCurrentInstance().addMessage("form_modificar_cambioDivisas:e_cambioDivisas", fm);	
			} catch (CuentaNoExisteException e) {
				FacesMessage fm = new FacesMessage("Esta cuenta no existe");
				FacesContext.getCurrentInstance().addMessage("form_modificar_cambioDivisas:e_cambioDivisas", fm);	
			} catch (CuentaNoSuporteadaException e) {
				FacesMessage fm = new FacesMessage("Cuenta no suporteada");
				FacesContext.getCurrentInstance().addMessage("form_modificar_cambioDivisas:e_cambioDivisas", fm);	

			}
			
			return null;
		}
		
		private static byte[] hashPassword(byte[] salt, byte[] password) {
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				digest.update(salt);
				return digest.digest(password);

			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}
		
		private static boolean comprobarPw(Usuario u, String pw) {
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				digest.update(parseByte(u.getSalt()));
				return MessageDigest.isEqual(parseByte(u.getPassword()), digest.digest(pw.getBytes()));

			} catch (Exception e) {
				// TODO: handle exception
			}
			return false;
		}

		private static String parseString(byte[] ba) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < ba.length; i++) {
				sb.append(Integer.toString((ba[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();
		}

		private static byte[] parseByte(String str) {
			byte[] ans = new byte[str.length() / 2];
			for (int i = 0; i < ans.length; i++) {
				int index = i * 2;
				int val = Integer.parseInt(str.substring(index, index + 2), 16);
				ans[i] = (byte) val;
			}

			return ans;
		}
	
	
}
