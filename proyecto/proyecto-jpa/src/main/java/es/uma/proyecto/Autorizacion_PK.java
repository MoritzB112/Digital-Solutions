package es.uma.proyecto;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
@Embeddable
public class Autorizacion_PK implements Serializable {
	private Long paID;
	private Long ciID;
	
	public Autorizacion_PK() {
		// TODO Auto-generated constructor stub
	}

	public Long getPaID() {
		return paID;
	}

	public void setPaID(Long paID) {
		this.paID = paID;
	}

	public Long getCiID() {
		return ciID;
	}

	public void setCiID(Long ciID) {
		this.ciID = ciID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ciID, paID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autorizacion_PK other = (Autorizacion_PK) obj;
		return Objects.equals(ciID, other.ciID) && Objects.equals(paID, other.paID);
	}

	@Override
	public String toString() {
		return "Autorizacion_PK [paID=" + paID + ", ciID=" + ciID + "]";
	}
	
	
}
