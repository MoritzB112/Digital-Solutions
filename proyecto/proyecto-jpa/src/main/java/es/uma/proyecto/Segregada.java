package es.uma.proyecto;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Segregada extends Cuenta_Fintech {
	private Double comision;
	
	@OneToOne(optional = false)
	private Cuenta_Referencia cr;
	
	public Segregada() {
		// TODO Auto-generated constructor stub
	}
	
	public Double getComision() {
		return comision;
	}
	public void setComision(Double comision) {
		this.comision = comision;
	}
	public Cuenta_Referencia getCr() {
		return cr;
	}
	public void setCr(Cuenta_Referencia cr) {
		this.cr = cr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(comision);
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
		Segregada other = (Segregada) obj;
		return Objects.equals(comision, other.comision);
	}

	@Override
	public String toString() {
		return "Segregada [comision=" + comision + "]";
	}
	
	
}
