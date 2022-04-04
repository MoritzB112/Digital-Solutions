package es.uma.proyecto;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Cuenta_Referencia extends Cuenta {
	@Column(nullable = false)
	private String nombreBanco;
	
	private String sucursal;
	
	private String pais;
	
	@Column(nullable = false)
	private Double saldo;
	
	@Temporal(TemporalType.DATE)
	private Date fecha_apertura;
	
	private String estado;
	
	@OneToOne(mappedBy="cr")
	private Segregada se;
	
	@ManyToOne(optional = false)
	private Divisa div;
	
	@OneToMany(mappedBy = "cr")
	private List<Depositado_en> deps;
	
	public Cuenta_Referencia() {
		// TODO Auto-generated constructor stub
	}
	
	public String getNombreBanco() {
		return nombreBanco;
	}
	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	public Date getFecha_apertura() {
		return fecha_apertura;
	}
	public void setFecha_apertura(Date fecha_apertura) {
		this.fecha_apertura = fecha_apertura;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Segregada getSe() {
		return se;
	}
	public void setSe(Segregada se) {
		this.se = se;
	}
	public Divisa getDiv() {
		return div;
	}
	public void setDiv(Divisa div) {
		this.div = div;
	}
	public List<Depositado_en> getDeps() {
		return deps;
	}
	public void setDeps(List<Depositado_en> deps) {
		this.deps = deps;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(estado, fecha_apertura, nombreBanco, pais, saldo, sucursal);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuenta_Referencia other = (Cuenta_Referencia) obj;
		return Objects.equals(estado, other.estado) && Objects.equals(fecha_apertura, other.fecha_apertura)
				&& Objects.equals(nombreBanco, other.nombreBanco) && Objects.equals(pais, other.pais)
				&& Objects.equals(saldo, other.saldo) && Objects.equals(sucursal, other.sucursal);
	}

	@Override
	public String toString() {
		return "Cuenta_Referencia [nombreBanco=" + nombreBanco + ", sucursal=" + sucursal + ", pais=" + pais
				+ ", saldo=" + saldo + ", fecha_apertura=" + fecha_apertura + ", estado=" + estado + "]";
	}
	
	
}
