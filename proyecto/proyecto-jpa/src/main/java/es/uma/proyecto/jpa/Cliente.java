package es.uma.proyecto.jpa;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CLIENTE")
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente {
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String tipo_cliente;

	@Column(nullable = false)
	private String estado;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fecha_alta;

	@Temporal(TemporalType.DATE)
	private Date fecha_baja;

	@Column(nullable = false)
	private String direccion;

	@Column(nullable = false)
	private String ciudad;

	@Column(nullable = false)
	private Integer codigoPostal;

	@Column(nullable = false)
	private String pais;
	
	@OneToOne
	private Usuario us;
	
	@OneToMany(mappedBy = "cl", fetch = FetchType.LAZY)
	private List<Cuenta_Fintech> cf;

	public Cliente() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo_cliente() {
		return tipo_cliente;
	}

	public void setTipo_cliente(String tipo_cliente) {
		this.tipo_cliente = tipo_cliente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecha_alta() {
		return fecha_alta;
	}

	public void setFecha_alta(Date fecha_alta) {
		this.fecha_alta = fecha_alta;
	}

	public Date getFecha_baja() {
		return fecha_baja;
	}

	public void setFecha_baja(Date fecha_baja) {
		this.fecha_baja = fecha_baja;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public List<Cuenta_Fintech> getCf() {
		return cf;
	}

	public void setCf(List<Cuenta_Fintech> cf) {
		this.cf = cf;
	}

	public Usuario getUs() {
		return us;
	}

	public void setUs(Usuario us) {
		this.us = us;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", tipo_cliente=" + tipo_cliente + ", estado=" + estado + ", fecha_alta="
				+ fecha_alta + ", fecha_baja=" + fecha_baja + ", direccion=" + direccion + ", ciudad=" + ciudad
				+ ", codigoPostal=" + codigoPostal + ", pais=" + pais + "]";
	}

	
}