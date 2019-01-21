package jpabook.start.objectOrientedQuery.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH10_OOQ_PRODUCT")
@SequenceGenerator(
	name = "CH10_OOQ_PRODUCT_SEQUENCE"
	, sequenceName = "CH10_OOQ_PRODUCT_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH10_OOQ_PRODUCT_SEQ")
	@Column(name = "PRODUCT_ID")
	private long id;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Order> order = new ArrayList<>(); 
	
	private String name;
	
	private int price;
	
	private int stockAmount;
	
	public Product() { }
	
	public Product(String name, int price, int stockAmount) {
		this.name = name;
		this.price = price;
		this.stockAmount = stockAmount;
	}

	public long getId() { return id; }

	public List<Order> getOrder() { return order; }
	public void addOrder(Order order) {
		if(!this.order.contains(order)) {
			this.order.add(order);
		}
		
		if(order.getProduct() != this) {
			order.setProduct(this);
		}
	}
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }

	public int getStockAmount() { return stockAmount; }
	public void setStockAmount(int stockAmount) { this.stockAmount = stockAmount; }
	
	
}
