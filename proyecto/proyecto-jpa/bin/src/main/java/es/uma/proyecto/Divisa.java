import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="DIVISA") 
public class Divisa {
	
	@Id	@Column(name="abreviatura", nullable=false)
	private String abreviatura;
	
	@Column(name="nombre", nullable=false)
	private String nombre;
	
	@Column(name="simbolo")
	private String simbolo;
	
	@Column(name="CambioEuro", nullable=false)
	private double CambioEuro;
	
	@OneToMany(fetch=FetchType.LAZY)
	private List<Transaccion> DivisaReceptor;
	
	@OneToMany(fetch=FetchType.LAZY)
	private List<Transaccion> DivisaEmisor;
	
	
	public Divisa(String abreviatura, String nombre, String simbolo, double CambioEuro) {
		super();
		this.abreviatura = abreviatura;
		this.nombre = nombre;
		this.simbolo = simbolo;
		this.CambioEuro = CambioEuro;
	}

	public Divisa() {
		
	}	
	
	/* Getters and Setter methods */

	public String getAbreviatura() {
		return abreviatura;
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

	public double getCambioEuro() {
		return CambioEuro;
	}

	public void setCambioEuro(double cambioEuro) {
		this.CambioEuro = cambioEuro;
	}
	
	public List<Transaccion> getDivisaReceptor() {
		return DivisaReceptor;
	}

	public void setDivisaReceptor(List<Transaccion> divisaReceptor) {
		this.DivisaReceptor = divisaReceptor;
	}

	public List<Transaccion> getDivisaEmisor() {
		return DivisaEmisor;
	}

	public void setDivisaEmisor(List<Transaccion> divisaEmisor) {
		this.DivisaEmisor = divisaEmisor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abreviatura == null) ? 0 : abreviatura.hashCode());
		return result;
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
		if (abreviatura == null) {
			if (other.abreviatura != null)
				return false;
		} else if (!abreviatura.equalsIgnoreCase(other.abreviatura))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Divisa [abreviatura=" + abreviatura + ", nombre=" + nombre 
				+ ", simbolo=" + simbolo + ", CambioEuro=" + CambioEuro + "]";
	}
	
}