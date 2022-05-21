package es.uma.proyecto.jpa;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
@Embeddable
public class Autorizacion_PK implements Serializable {
	private Long paID;
	private Long emID;
	
	public Autorizacion_PK() {
		// TODO Auto-generated constructor stub
	}

	public Long getPaID() {
		return paID;
	}

	public void setPaID(Long paID) {
		this.paID = paID;
	}

	public Long getEmID() {
		return emID;
	}

	public void setEmID(Long emID) {
		this.emID = emID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(emID, paID);
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
		return Objects.equals(emID, other.emID) && Objects.equals(paID, other.paID);
	}

	@Override
	public String toString() {
		return "Autorizacion_PK [paID=" + paID + ", emID=" + emID + "]";
	}
	
	
}
