package es.uma.proyecto.api;

import java.util.Objects;

public class ClientInput {
	
	private Client searchParameters;

	public Client getSearchParameters() {
		return searchParameters;
	}

	public void setSearchParameters(Client searchParameters) {
		this.searchParameters = searchParameters;
	}

	@Override
	public int hashCode() {
		return Objects.hash(searchParameters);
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
		return Objects.equals(searchParameters, other.searchParameters);
	}

	@Override
	public String toString() {
		return "ClientInput [searchParameters=" + searchParameters + "]";
	}
	
}
