package model2.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name = "CH05_MODEL2_ORDERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Order {

	@Id @GeneratedValue
	@Column(name = "ORDER_ID")
	private Long orderId;

	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	@OneToMany(mappedBy = "order")	
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public Order() { }
	public Order(Date orderDate, OrderStatus status) {
		this.orderDate = orderDate;
		this.status = status;
	}
	
	public Order(Date orderDate, OrderStatus status, Member member) {
		this.orderDate = orderDate;
		this.status = status;
		this.member = member;
	}
	
	public Long getOrderId() { return orderId; }
	
	public Date getOrderDate() { return orderDate; }
	public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
	
	public OrderStatus getStatus() { return status; }
	public void setStatus(OrderStatus status) { this.status = status; }
	
	public Member getMember() { return this.member; }
	public void setMember(Member member) {
		Optional.ofNullable(this.member)
			.ifPresent(m -> {
				m.getOrder().remove(this);
			});
		
		this.member = member;
		member.getOrder().add(this);
	}
	
	public List<OrderItem> getOrderItem() { return this.orderItems; }
	public void setOrderItem(List<OrderItem> orderItems) { this.orderItems = orderItems; }
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
	
}
