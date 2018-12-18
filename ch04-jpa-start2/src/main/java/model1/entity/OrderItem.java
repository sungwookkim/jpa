package model1.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "CH04_MODEL1_ORDER_ITEM")
public class OrderItem {
	
	@Id @GeneratedValue
	@Column(name = "ORDER_ITEM_ID")
	private Long id;
	
	@Column(name = "ITEM_ID")
	private Long itemId;
	
	@Column(name = "ORDER_ID")
	private Long orderId;
	
	private int orderPrice;
	private int count;
	
	public OrderItem() { }
	public OrderItem(Long itemId, Long orderId, int orderPrice, int count) {
		this.itemId = itemId;
		this.orderId = orderId;
		this.orderPrice = orderPrice;
		this.count = count;
	}
	
	public Long getId() { return id; }
	
	public Long getItemId() { return itemId; }
	public void setItemId(Long itemId) { this.itemId = itemId; }
	
	public Long getOrderId() { return orderId; }
	public void setOrderId(Long orderId) { this.orderId = orderId; }
	
	public int getOrderPrice() { return orderPrice; }
	public void setOrderPrice(int orderPrice) { this.orderPrice = orderPrice; }
	
	public int getCount() { return count; }
	public void setCount(int count) { this.count = count; }
	
	@Override
	public String toString() {
		return "{"
			+ "'id' : '" + Optional.ofNullable(this.id)
				.map(id -> String.valueOf(id)).orElse("") + "'"
				
			+ ", 'itemId' : '" + Optional.ofNullable(this.itemId)
				.map(itemId -> String.valueOf(itemId)).orElse("") + "'"
				
			+ ", 'orderId' : '" + Optional.ofNullable(this.orderId)
				.map(orderId -> String.valueOf(orderId)).orElse("") + "'"
				
			+ ", 'orderPrice' : '" + Optional.ofNullable(this.orderPrice).orElse(0) + "'"
			+ ", 'count' : '" + Optional.ofNullable(this.count).orElse(0) + "'"			
			+ "}";
	}

}
