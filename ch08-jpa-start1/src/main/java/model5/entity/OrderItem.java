package model5.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import model5.entity.item.mapping.Item;
import model5.entity.superClass.BaseEntity;

@Entity(name = "CH08_MODEL5_ORDER_ITEM")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class OrderItem extends BaseEntity {
	
	@Id @GeneratedValue
	@Column(name = "ORDER_ITEM_ID")
	private Long orderItemId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "ITEM_ID")
	private Item item;
	
	private int orderPrice;
	private int count;
	
	public OrderItem() { }

	public OrderItem(int orderPrice, int count) {
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
		Optional.ofNullable(this.order)
			.ifPresent(o -> o.getOrderItem().remove(this));

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
