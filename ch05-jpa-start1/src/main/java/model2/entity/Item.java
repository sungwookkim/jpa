package model2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name = "CH05_MODEL2_ITEM")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
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
}
