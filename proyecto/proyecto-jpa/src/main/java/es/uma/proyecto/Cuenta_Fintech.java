package es.uma.proyecto;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Cuenta_Fintech extends Cuenta {
	@Column(nullable = false)
	private String estado;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fecha_apertura;
	
	@Temporal(TemporalType.DATE)
	private Date fecha_cierre;
	
	private String clasificacion;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Cliente cl;
	
	public Cuenta_Fintech() {
		// TODO Auto-generated constructor stub
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFecha_apertura() {
		return fecha_apertura;
	}

	public void setFecha_apertura(Date fecha_apertura) {
		this.fecha_apertura = fecha_apertura;
	}

	public Date getFecha_cierre() {
		return fecha_cierre;
	}

	public void setFecha_cierre(Date fecha_cierre) {
		this.fecha_cierre = fecha_cierre;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public Cliente getCl() {
		return cl;
	}

	public void setCl(Cliente cl) {
		this.cl = cl;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuenta_Fintech other = (Cuenta_Fintech) obj;
		return Objects.equals(super.getIBAN(), other.getIBAN());
	}

	@Override
	public String toString() {
		return "Cuenta_Fintech [estado=" + estado + ", fecha_apertura=" + fecha_apertura + ", fecha_cierre="
				+ fecha_cierre + ", clasificacion=" + clasificacion + "]";
	}
	
	
}
