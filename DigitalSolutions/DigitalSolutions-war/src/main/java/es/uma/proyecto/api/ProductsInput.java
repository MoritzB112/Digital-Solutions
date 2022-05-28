package es.uma.proyecto.api;

import java.util.Date;
import java.util.Objects;

public class ProductsInput {

	private Product searchParameters;

	public Product getSearchParameters() {
		return searchParameters;
	}

	public void setSearchParameters(Product searchParameters) {
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
		ProductsInput other = (ProductsInput) obj;
		return Objects.equals(searchParameters, other.searchParameters);
	}

	@Override
	public String toString() {
		return "ClientInput [searchParameters=" + searchParameters + "]";
	}
	
	

}
