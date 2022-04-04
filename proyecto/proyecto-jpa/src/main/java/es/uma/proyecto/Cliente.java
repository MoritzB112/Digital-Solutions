package es.uma.proyecto;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CLIENTE")
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "identificacion", unique = true, nullable = false)
	private String identificacion;

	@Column(name = "tipo_cliente", nullable = false)
	private String tipo_cliente;

	@Column(name = "estado", nullable = false)
	private String estado;

	@Column(name = "fecha_alta", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fecha_alta;

	@Column(name = "fecha_baja")
	@Temporal(TemporalType.DATE)
	private Date fecha_baja;

	@Column(name = "direccion", nullable = false)
	private String direccion;

	@Column(name = "ciudad", nullable = false)
	private String ciudad;

	@Column(name = "codigoPostal", nullable = false)
	private Integer codigoPostal;

	@Column(name = "pais", nullable = false)
	private String pais;
	
	private String password;
	
	private String salt;
	
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

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public List<Cuenta_Fintech> getCf() {
		return cf;
	}

	public void setCf(List<Cuenta_Fintech> cf) {
		this.cf = cf;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ciudad, codigoPostal, direccion, estado, fecha_alta, fecha_baja, id, identificacion, pais,
				password, salt, tipo_cliente);
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
		return Objects.equals(ciudad, other.ciudad) && Objects.equals(codigoPostal, other.codigoPostal)
				&& Objects.equals(direccion, other.direccion) && Objects.equals(estado, other.estado)
				&& Objects.equals(fecha_alta, other.fecha_alta) && Objects.equals(fecha_baja, other.fecha_baja)
				&& Objects.equals(id, other.id) && Objects.equals(identificacion, other.identificacion)
				&& Objects.equals(pais, other.pais) && Objects.equals(password, other.password)
				&& Objects.equals(salt, other.salt) && Objects.equals(tipo_cliente, other.tipo_cliente);
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", identificacion=" + identificacion + ", tipo_cliente=" + tipo_cliente
				+ ", estado=" + estado + ", fecha_alta=" + fecha_alta + ", fecha_baja=" + fecha_baja + ", direccion="
				+ direccion + ", ciudad=" + ciudad + ", codigoPostal=" + codigoPostal + ", pais=" + pais + ", password="
				+ password + ", salt=" + salt + "]";
	}

	
}