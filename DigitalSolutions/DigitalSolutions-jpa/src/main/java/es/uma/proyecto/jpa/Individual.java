package es.uma.proyecto.jpa;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Individual extends Cliente {
	
	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false)
	private String apellido;
	
	@Temporal(TemporalType.DATE)
	private Date fecha_nacimiento;
	
	public Individual() {
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Date getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(Date fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Individual other = (Individual) obj;
		return Objects.equals(super.getId(), other.getId());
	}

	@Override
	public String toString() {
		return "Individual [nombre=" + nombre + ", apellido=" + apellido + ", fecha_nacimiento=" + fecha_nacimiento
				+ "]";
	}
	
	
}
