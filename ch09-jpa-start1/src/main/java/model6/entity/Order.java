package model6.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import model6.entity.superClass.BaseEntity;

@Entity(name = "CH09_MODEL6_ORDERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Order extends BaseEntity {

	@Id @GeneratedValue
	@Column(name = "ORDER_ID")
	private Long orderId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "DELIVERY_ID")
	private Delivery delivery;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public Order() { }
	public Order(Date orderDate, OrderStatus status) {
		this.orderDate = orderDate;
		this.status = status;
	}
	
	public Order(Date orderDate, OrderStatus status, Member member, Delivery delivery) {
		this.orderDate = orderDate;
		this.status = status;
		this.member = member;
		this.delivery = delivery;
	}
	
	public Long getOrderId() { return orderId; }
	
	public Date getOrderDate() { return orderDate; }
	public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
	
	public OrderStatus getStatus() { return status; }
	public void setStatus(OrderStatus status) { this.status = status; }
	
	public Delivery getDelivery() { return this.delivery; }
	public void setDelivery(Delivery delivery) { 
		this.delivery = delivery;

		if(delivery.getOrder() != this) {
			delivery.setOrder(this);
		}
	}
	
	public Member getMember() { return this.member; }
	public void setMember(Member member) {
		Optional.ofNullable(this.member)
			.ifPresent(m -> {
				m.getOrder().remove(this);
			});

		this.member = member;

		// 무한루프에 빠지지 않도록 체크. 
		if(!member.getOrder().contains(this)) {
			member.getOrder().add(this);	
		}
	}
	
	public List<OrderItem> getOrderItem() { return this.orderItems; }
	public void addOrderItem(OrderItem orderItem) {
		if(!this.orderItems.contains(orderItem)) {
			this.orderItems.add(orderItem);
		}

		// 무한루프에 빠지지 않도록 체크.
		if(orderItem.getOrder() != this) {
			orderItem.setOrder(this);	
		}
	}	
}
