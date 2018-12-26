package model2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name = "CH05_MODEL2_ORDER_ITEM")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class OrderItem {
	
	@Id @GeneratedValue
	@Column(name = "ORDER_ITEM_ID")
	private Long orderItemId;
	
	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "ITEM_ID")
	private Item item;
	
	private int orderPrice;
	private int count;
	
	public OrderItem() { }
	public OrderItem(Item item, int orderPrice, int count) {
		this.item = item;
		this.orderPrice = orderPrice;
		this.count = count;
	}

	public OrderItem(Item item, Order order, int orderPrice, int count) {
		this.item = item;
		this.order = order;
		this.orderPrice = orderPrice;
		this.count = count;
	}

	public Long getId() { return orderItemId; }

	public Item getItem() { return item; }
	public void setItem(Item item) { this.item = item; }

	public Order getOrder() { return order; }
	public void setOrder(Order order) { 
		this.order = order;
		
		// 무한루프에 빠지지 않도록 체크. 
		if(!order.getOrderItem().contains(this)) {
			order.getOrderItem().add(this);
		}
	}

	public int getOrderPrice() { return orderPrice; }
	public void setOrderPrice(int orderPrice) { this.orderPrice = orderPrice; }

	public int getCount() { return count; }
	public void setCount(int count) { this.count = count; }
	
}
