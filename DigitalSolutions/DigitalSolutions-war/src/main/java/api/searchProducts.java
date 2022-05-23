package api;

import javax.json.bind.annotation.JsonbProperty;

public class searchProducts {
	@JsonbProperty("status")
	private String status;
	@JsonbProperty("productNumber")
	private String productNumber;
	
	
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
	
	
}