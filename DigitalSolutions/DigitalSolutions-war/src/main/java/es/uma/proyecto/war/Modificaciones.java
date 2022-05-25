package es.uma.proyecto.war;

import java.io.Serializable;

import javax.faces.view.ViewScoped;

import es.uma.proyecto.jpa.Empresa;


@ViewScoped
public class Modificaciones implements Serializable{
	private Long id;
	private Empresa em;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Empresa getEm() {
		return em;
	}

	public void setEm(Empresa em) {
		this.em = em;
	}
	
	
	
	
}
