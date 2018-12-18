package model1.entity;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "CH04_MODEL1_ORDERS")
public class Order {

	@Id @GeneratedValue
	@Column(name = "ORDER_ID")
	private Long id;
	
	@Column(name = "MEMBER_ID")
	private Long memberId;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	public Order() { }	
	public Order(Long memberId, Date orderDate, OrderStatus status) {		
		this.memberId = memberId;
		this.orderDate = orderDate;
		this.status = status;
	}
	
	public Long getId() { return id; }
	
	public Long getMemberId() { return memberId; }	
	public void setMemberId(Long memberId) { this.memberId = memberId; }
	
	public Date getOrderDate() { return orderDate; }
	public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
	
	public OrderStatus getStatus() { return status; }
	public void setStatus(OrderStatus status) { this.status = status; }
	
	@SuppressWarnings("deprecation")
	@Override
	public String toString() {
		return "{"
			+ "'id' : '" + Optional.ofNullable(this.id)
				.map(id -> String.valueOf(id)).orElse("") + "'"
			
			+ ", 'memberId' : '" + Optional.ofNullable(this.memberId)
				.map(memberId -> String.valueOf(memberId)).orElse("") + "'"
			
			+ ", 'orderDate' : '" + Optional.ofNullable(this.orderDate)
				.map(date -> date.toGMTString()).orElse("") + "'"
				
			+ ", 'status' : '" + Optional.ofNullable(this.status)
				.map(status -> status.toString()).orElse("") + "'"
			+ "}";
	}
}
