package es.uma.proyecto.api;

import java.util.Objects;

public class Nombre {

	private String firstName;
	
	private String lastName;
	
	public Nombre() {
		//TODO Auto-generated constructor stub
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nombre other = (Nombre) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(lastName, other.lastName);
	}

	@Override
	public String toString() {
		return "Nombre [firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
}
