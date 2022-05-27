package es.uma.proyecto.api;

import java.util.Date;
import java.util.Objects;

public class ClientInput {
	
	public String name;
	
	public String lastName;
	
	public Date startPeriod;
	
	public Date endPeriod;
	
	public ClientInput() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(Date startPeriod) {
		this.startPeriod = startPeriod;
	}

	public Date getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(Date endPeriod) {
		this.endPeriod = endPeriod;
	}

	@Override
	public int hashCode() {
		return Objects.hash(endPeriod, lastName, name, startPeriod);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientInput other = (ClientInput) obj;
		return Objects.equals(endPeriod, other.endPeriod) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(name, other.name) && Objects.equals(startPeriod, other.startPeriod);
	}

	@Override
	public String toString() {
		return "ClientInput [name=" + name + ", lastName=" + lastName + ", startPeriod=" + startPeriod + ", endPeriod="
				+ endPeriod + "]";
	}

}
