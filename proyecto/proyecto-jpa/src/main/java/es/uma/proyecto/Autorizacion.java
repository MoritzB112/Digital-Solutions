package es.uma.proyecto;

import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class Autorizacion {
	@EmbeddedId
	private Autorizacion_PK id;
	
	private String tipo;
	
	@MapsId("paID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Persona_Autorizada pa;
	
	@MapsId("emID")
	@ManyToOne(fetch = FetchType.LAZY)
	private Empresa em;
	
	public Autorizacion() {
		// TODO Auto-generated constructor stub
	}
	
	public Autorizacion_PK getId() {
		return id;
	}
	public void setId(Autorizacion_PK id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Persona_Autorizada getPa() {
		return pa;
	}
	public void setPa(Persona_Autorizada pa) {
		this.pa = pa;
	}
	public Empresa getEm() {
		return em;
	}
	public void setEm(Empresa em) {
		this.em = em;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autorizacion other = (Autorizacion) obj;
		return Objects.equals(id, other.id) && Objects.equals(tipo, other.tipo);
	}

	@Override
	public String toString() {
		return "Autorizacion [id=" + id + ", tipo=" + tipo + "]";
	}
	
	
}
