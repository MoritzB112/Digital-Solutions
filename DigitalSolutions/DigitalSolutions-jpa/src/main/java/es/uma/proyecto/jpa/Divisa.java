package es.uma.proyecto.jpa;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Divisa {
	@Id
	private String abreviatura;
	
	@Column(nullable=false)
	private String nombre;
	
	private String simbolo;
	
	@Column(nullable=false)
	private Double CambioEuro;
	
	@OneToMany(mappedBy = "divRec", fetch = FetchType.LAZY)
	private List<Transaccion> divPago;
	
	@OneToMany(mappedBy = "divEm", fetch = FetchType.LAZY)
	private List<Transaccion> divCobro;
	
	@OneToMany(mappedBy = "div", fetch = FetchType.LAZY)
	private List<Cuenta_Referencia> cr;
	
	public Divisa() {
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public Double getCambioEuro() {
		return CambioEuro;
	}

	public void setCambioEuro(Double cambioEuro) {
		CambioEuro = cambioEuro;
	}

	public List<Transaccion> getDivPago() {
		return divPago;
	}

	public void setDivPago(List<Transaccion> divPago) {
		this.divPago = divPago;
	}

	public List<Transaccion> getDivCobro() {
		return divCobro;
	}

	public void setDivCobro(List<Transaccion> divCobro) {
		this.divCobro = divCobro;
	}

	public List<Cuenta_Referencia> getCr() {
		return cr;
	}

	public void setCr(List<Cuenta_Referencia> cr) {
		this.cr = cr;
	}

	@Override
	public int hashCode() {
		return Objects.hash(abreviatura);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Divisa other = (Divisa) obj;
		return Objects.equals(abreviatura, other.abreviatura);
	}

	@Override
	public String toString() {
		return "Divisa [abreviatura=" + abreviatura + ", nombre=" + nombre + ", simbolo=" + simbolo + ", CambioEuro="
				+ CambioEuro + "]";
	}
	

}
