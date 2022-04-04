package es.uma.proyecto;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class Depositado_en_PK implements Serializable {
	private String crID;
	
	private String paID;
	
	public Depositado_en_PK() {
		// TODO Auto-generated constructor stub
	}

	public String getCrID() {
		return crID;
	}

	public void setCrID(String crID) {
		this.crID = crID;
	}

	public String getPaID() {
		return paID;
	}

	public void setPaID(String paID) {
		this.paID = paID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(paID, crID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Depositado_en_PK other = (Depositado_en_PK) obj;
		return Objects.equals(paID, other.paID) && Objects.equals(crID, other.crID);
	}

	@Override
	public String toString() {
		return "Depositado_en_PK [crID=" + crID + ", paID=" + paID + "]";
	}
	
	
}
