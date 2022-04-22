package es.uma.proyecto;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Persona_Autorizada {
	@Id
	@GeneratedValue
	private Long ID;

	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private String apellidos;
	
	@Column(nullable = false)
	private String direccion;
	
	@Temporal(TemporalType.DATE)
	private Date fecha_nacimeinteo;
	
	private String estado;
	
	@Temporal(TemporalType.DATE)
	private Date FechaInicio;
	
	@Temporal(TemporalType.DATE)
	private Date FechaFin;
	
	@OneToMany(mappedBy = "pa", fetch = FetchType.LAZY)
	private List<Autorizacion> autorizaciones;
	
	@OneToOne(optional = false)
	private Usuario us;

	public Persona_Autorizada() {
		// TODO Auto-generated constructor stub
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getFecha_nacimeinteo() {
		return fecha_nacimeinteo;
	}

	public void setFecha_nacimeinteo(Date fecha_nacimeinteo) {
		this.fecha_nacimeinteo = fecha_nacimeinteo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaInicio() {
		return FechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		FechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return FechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		FechaFin = fechaFin;
	}

	public List<Autorizacion> getAutorizaciones() {
		return autorizaciones;
	}

	public void setAutorizaciones(List<Autorizacion> autorizaciones) {
		this.autorizaciones = autorizaciones;
	}

	public Usuario getUs() {
		return us;
	}

	public void setUs(Usuario us) {
		this.us = us;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona_Autorizada other = (Persona_Autorizada) obj;
		return Objects.equals(ID, other.ID);
	}

	@Override
	public String toString() {
		return "Persona_Autorizada [ID=" + ID + ", nombre=" + nombre + ", apellidos=" + apellidos + ", direccion="
				+ direccion + ", fecha_nacimeinteo=" + fecha_nacimeinteo + ", estado=" + estado + ", FechaInicio="
				+ FechaInicio + ", FechaFin=" + FechaFin + "]";
	}
	
}
