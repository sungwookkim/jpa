package jpabook.start.objectOrientedQuery.queryDSL.queryDSLProjection;

public class ProductDTO {

	private String productName;
	private int stockAmount;
	
	public ProductDTO() {}
	
	public ProductDTO(String productName, int stockAmount) {
		this.productName = productName;
		this.stockAmount = stockAmount;
	}

	public String getProductName() { return productName; }
	public int getStockAmount() { return stockAmount; }

	public void setProductName(String productName) { this.productName = productName; }
	public void setStockAmount(int stockAmount) { this.stockAmount = stockAmount; }
}
