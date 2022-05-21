package es.uma.proyecto.jpa;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Empresa extends Cliente {
	
	@Column(nullable = false)
	private String razon_social;
	
	@OneToMany(mappedBy = "em", fetch = FetchType.LAZY)
	private List<Autorizacion> au;
	
	public Empresa() {
	
	}

	public String getRazon_social() {
		return razon_social;
	}

	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}

	public List<Autorizacion> getAu() {
		return au;
	}

	public void setAu(List<Autorizacion> au) {
		this.au = au;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		return Objects.equals(super.getId(), other.getId());
	}

	@Override
	public String toString() {
		return "Empresa [razon_social=" + razon_social + "]";
	}
	
	
}
