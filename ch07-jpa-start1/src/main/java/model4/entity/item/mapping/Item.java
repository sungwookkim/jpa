package model4.entity.item.mapping;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import model4.entity.superClass.BaseEntity;

@Entity(name = "CH07_MODEL4_ITEM")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class Item extends BaseEntity {
		
	@Id @GeneratedValue
	@Column(name = "ITEM_ID")
	private Long id;
	
	@Column(name = "DTYPE", nullable = false, updatable = false, insertable = false)
	private String dType;
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
	public String getdType() { return dType; }
	
	public String getName() { return name; }	
	public void setName(String name) { this.name = name; }
	
	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }
	
	public int getStockquantity() { return stockquantity; }
	public void setStockquantity(int stockquantity) { this.stockquantity = stockquantity; }	

}
