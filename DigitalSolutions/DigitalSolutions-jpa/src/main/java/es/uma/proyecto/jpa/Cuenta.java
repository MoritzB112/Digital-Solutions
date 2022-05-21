package es.uma.proyecto.jpa;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Cuenta {
	@Id
	private String IBAN;
	
	private String SWIFT;
	
	@OneToMany(mappedBy="destino", fetch = FetchType.LAZY)
	private List<Transaccion> cobros;
	
	@OneToMany(mappedBy="origen", fetch = FetchType.LAZY)
	private List<Transaccion> pagos;

	public Cuenta() {
		// TODO Auto-generated constructor stub
	}

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}

	public String getSWIFT() {
		return SWIFT;
	}

	public void setSWIFT(String sWIFT) {
		SWIFT = sWIFT;
	}

	public List<Transaccion> getCobros() {
		return cobros;
	}

	public void setCobros(List<Transaccion> cobros) {
		this.cobros = cobros;
	}

	public List<Transaccion> getPagos() {
		return pagos;
	}

	public void setPagos(List<Transaccion> pagos) {
		this.pagos = pagos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(IBAN);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuenta other = (Cuenta) obj;
		return Objects.equals(IBAN, other.IBAN);
	}

	@Override
	public String toString() {
		return "Cuenta [IBAN=" + IBAN + ", SWIFT=" + SWIFT + "]";
	}
	
	
}
