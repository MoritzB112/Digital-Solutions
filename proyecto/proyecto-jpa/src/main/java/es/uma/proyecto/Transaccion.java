package es.uma.proyecto;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Transaccion {
	@Id
	@GeneratedValue
	private Long ID_unico;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date fechaInstruccion;
	
	@Column(nullable=false)
	private Double cantidad;
	
	@Temporal(TemporalType.DATE)
	private Date fechaEjecucion;
	
	@Column(nullable=false)
	private String Tipo;
	
	private Double comision;
	
	private Boolean internacional;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Cuenta origen;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Cuenta destino;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Divisa divRec;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Divisa divEm;
	
	public Transaccion() {	
	}

	public Long getID_unico() {
		return ID_unico;
	}

	public void setID_unico(Long iD_unico) {
		ID_unico = iD_unico;
	}

	public Date getFechaInstruccion() {
		return fechaInstruccion;
	}

	public void setFechaInstruccion(Date fechaInstruccion) {
		this.fechaInstruccion = fechaInstruccion;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public String getTipo() {
		return Tipo;
	}

	public void setTipo(String tipo) {
		Tipo = tipo;
	}

	public Double getComision() {
		return comision;
	}

	public void setComision(Double comision) {
		this.comision = comision;
	}

	public Boolean getInternacional() {
		return internacional;
	}

	public void setInternacional(Boolean internacional) {
		this.internacional = internacional;
	}

	public Cuenta getOrigen() {
		return origen;
	}

	public void setOrigen(Cuenta origen) {
		this.origen = origen;
	}

	public Cuenta getDestino() {
		return destino;
	}

	public void setDestino(Cuenta destino) {
		this.destino = destino;
	}

	public Divisa getDivRec() {
		return divRec;
	}

	public void setDivRec(Divisa divRec) {
		this.divRec = divRec;
	}

	public Divisa getDivEm() {
		return divEm;
	}

	public void setDivEm(Divisa divEm) {
		this.divEm = divEm;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID_unico, Tipo, cantidad, comision, fechaEjecucion, fechaInstruccion, internacional);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaccion other = (Transaccion) obj;
		return Objects.equals(ID_unico, other.ID_unico) && Objects.equals(Tipo, other.Tipo)
				&& Objects.equals(cantidad, other.cantidad) && Objects.equals(comision, other.comision)
				&& Objects.equals(fechaEjecucion, other.fechaEjecucion)
				&& Objects.equals(fechaInstruccion, other.fechaInstruccion)
				&& Objects.equals(internacional, other.internacional);
	}

	@Override
	public String toString() {
		return "Transaccion [ID_unico=" + ID_unico + ", fechaInstruccion=" + fechaInstruccion + ", cantidad=" + cantidad
				+ ", fechaEjecucion=" + fechaEjecucion + ", Tipo=" + Tipo + ", comision=" + comision
				+ ", internacional=" + internacional + "]";
	}
	
	

}
