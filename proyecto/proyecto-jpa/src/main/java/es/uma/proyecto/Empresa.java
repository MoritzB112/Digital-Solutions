package es.uma.proyecto;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

public class Empresa extends Cliente {
	
	@Column(name = "razon_social", nullable = false)
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
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(razon_social);
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
		Empresa other = (Empresa) obj;
		return Objects.equals(razon_social, other.razon_social);
	}

	@Override
	public String toString() {
		return "Empresa [razon_social=" + razon_social + "]";
	}
	
	
}
