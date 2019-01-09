package jpabook.start.loading.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH08_LOADING_PRODUCT")
@SequenceGenerator(
	name = "CH08_LOADING_PRODUCT_SEQUENCE"
	, sequenceName = "CH08_LOADING_PRODUCT_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH08_LOADING_PRODUCT_SEQ")
	@Column(name = "PRODUCT_ID")
	private long id;
	
	@OneToMany(mappedBy = "product")	
	private List<Order> order = new ArrayList<Order>();
	
	private String name;
	
	public Product() { }
	
	public Product(String name) {
		this.name = name;
	}

	long getId() { return id; }
	
	String getName() { return name; }
	void setName(String name) { this.name = name; }

	public List<Order> getOrder() { return order; }
	public void addOrder(Order order) {
		if(!this.order.contains(order)) {
			this.order.add(order);
		}
		
		if(order.getProduct() != this) {
			order.setProduct(this);
		}
	}
}
