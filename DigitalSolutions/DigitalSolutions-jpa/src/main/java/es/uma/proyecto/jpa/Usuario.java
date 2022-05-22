package es.uma.proyecto.jpa;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
public class Usuario {

	@Id
	private String usuario;

<<<<<<< HEAD
	@Lob @Basic(fetch= FetchType.LAZY)
	@Column(name="password", columnDefinition="BINARY(1000)")
	private byte[] password;

	@Lob @Basic(fetch= FetchType.LAZY)
	@Column(name="salt", columnDefinition="BINARY(1000)")
	private byte[] salt;
=======
	private String password;

//	@Basic(fetch = FetchType.LAZY)
//	@Lob
//	private byte[] salt;
>>>>>>> ac2396de82b5746024f0ac14ccf847ccb56b9132

	private String correo;

	@Column(nullable = false)
	private Boolean esAdministrativo;

	@OneToOne(mappedBy = "us", fetch = FetchType.LAZY)
	private Cliente cl;

	@OneToOne(mappedBy = "us", fetch = FetchType.LAZY)
	private Persona_Autorizada pa;

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}
	
<<<<<<< HEAD
	
=======
	public void setPassword(String password) {
		this.password = password;
	}
>>>>>>> ac2396de82b5746024f0ac14ccf847ccb56b9132

//	public byte[] getSalt() {
//		return salt;
//	}
//
//	public void setSalt(byte[] salt) {
//		this.salt = salt;
//	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Boolean getEsAdministrativo() {
		return esAdministrativo;
	}

	public void setEsAdministrativo(Boolean esAdministrativo) {
		this.esAdministrativo = esAdministrativo;
	}

	public Cliente getCl() {
		return cl;
	}

	public void setCl(Cliente cl) {
		this.cl = cl;
	}

	public Persona_Autorizada getPa() {
		return pa;
	}

	public void setPa(Persona_Autorizada pa) {
		this.pa = pa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(usuario, other.usuario);
	}

	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + ", password=" + password + ", correo=" + correo + ", esAdministrativo="
				+ esAdministrativo + "]";
	}

//	@Override
//	public String toString() {
//		return "Usuario [usuario=" + usuario + ", password=" + password + ", salt=" + salt + ", correo=" + correo
//				+ ", esAdministrativo=" + esAdministrativo + "]";
//	}

}
