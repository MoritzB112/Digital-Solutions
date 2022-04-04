package es.uma.proyecto;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class Depositado_en {
	@EmbeddedId
	private Depositado_en_PK id;
	
	@Column(nullable=false)
	private Double saldo;
	
	@MapsId("crID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Cuenta_Referencia cr;
	
	@MapsId("paID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Pooled_Account pa;
	
	public Depositado_en() {
	}

	public Depositado_en_PK getId() {
		return id;
	}

	public void setId(Depositado_en_PK id) {
		this.id = id;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public Cuenta_Referencia getCr() {
		return cr;
	}

	public void setCr(Cuenta_Referencia cr) {
		this.cr = cr;
	}

	public Pooled_Account getPa() {
		return pa;
	}

	public void setPa(Pooled_Account pa) {
		this.pa = pa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, saldo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Depositado_en other = (Depositado_en) obj;
		return Objects.equals(id, other.id) && Objects.equals(saldo, other.saldo);
	}

	@Override
	public String toString() {
		return "Depositado_en [id=" + id + ", saldo=" + saldo + "]";
	}
	
	
}

