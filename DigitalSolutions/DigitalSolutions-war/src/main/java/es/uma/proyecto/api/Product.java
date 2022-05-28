package es.uma.proyecto.api;

import java.util.Objects;

public class Product {

	private String status;

	private String productNumber;

	public Product() {
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(productNumber, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(productNumber, other.productNumber) && Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "Product [status=" + status + ", productNumber=" + productNumber + "]";
	}

}
