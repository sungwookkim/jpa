package model1.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "CH04_MODEL1_ITEM")
public class Item {
		
	@Id @GeneratedValue
	@Column(name = "ITEM_ID")
	private Long id;
	private String name;
	private int price;
	private int stockquantity;
	
	public Item() {}
	public Item(String name, int price, int stockquantity) {
		this.name = name;
		this.price = price;
		this.stockquantity = stockquantity;
	}
		
	public Long getId() { return id; }
	
	public String getName() { return name; }	
	public void setName(String name) { this.name = name; }
	
	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }
	
	public int getStockquantity() { return stockquantity; }
	public void setStockquantity(int stockquantity) { this.stockquantity = stockquantity; }
	
	@Override
	public String toString() {
		return "{"
			+ "'id' : '" + Optional.ofNullable(this.id)
				.map(id -> String.valueOf(id)).orElse("") + "'"
			
			+ ", 'name' : '" + Optional.ofNullable(this.name).orElse("") + "'"
			+ ", 'price' : '" + Optional.ofNullable(this.price).orElse(0) + "'"			
			+ ", 'stockquantity' : '" + Optional.ofNullable(this.stockquantity).orElse(0) + "'"
			+ "}";
	}
}
