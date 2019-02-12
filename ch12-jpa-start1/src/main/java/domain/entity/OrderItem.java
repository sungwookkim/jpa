package domain.entity;

import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import domain.entity.item.abs.Item;

@Entity(name = "CH12_ORDERITEM")
@SequenceGenerator(
	name = "CH12_ORDERITEM_SEQUENCE"
	, sequenceName = "CH12_ORDERITEM_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class OrderItem {

	@Id
	@Column(name = "ORDERITEM_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH12_ORDERITEM_SEQ")
	private long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ORDER_ID")
	private Order order;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ITEM_ID")
	private Item item;
	
	private int orderPrice;
	
	private int count;
	
	public OrderItem() { }
	
	public OrderItem(int orderPrice, int count) {
		this.orderPrice = orderPrice;
		this.count = count;
	}

	public long getId() { return id; }

	public Item getItem() { return item; }
	public void setItem(Item item) { this.item = item; }

	public Order getOrder() { return order; }
	public void setOrder(Order order) {
		Optional.ofNullable(this.order)
			.ifPresent(o -> order.getOrderItems().remove(this));

		this.order = order;
		
		if(!order.getOrderItems().contains(this)) {
			order.getOrderItems().add(this);
		}
	}
	
	public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
		OrderItem orderItem = new OrderItem(orderPrice, count);
		orderItem.setItem(item);
		
		item.removeStock(count);
		
		return orderItem;
	}
	
	public void cancle() {
		getItem().addStock(this.count);
	}
	
	public int getTotalPrice() {
		return getOrderPrice() * getCount();
	}
	
	public int getCount() { return count; }
	public void setCount(int count) { this.count = count; }
	
	public int getOrderPrice() { return orderPrice; }
	public void setOrderPrice(int orderPrice) { this.orderPrice = orderPrice; }
}
