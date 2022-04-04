package es.uma.proyecto;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Pooled_Account extends Cuenta_Fintech {
	@OneToMany(mappedBy = "pa")
	private List<Depositado_en> deps;
	
	public Pooled_Account() {
		// TODO Auto-generated constructor stub
	}

	public List<Depositado_en> getDeps() {
		return deps;
	}

	public void setDeps(List<Depositado_en> deps) {
		this.deps = deps;
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
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return "Pooled_Account []";
	}

	
}
