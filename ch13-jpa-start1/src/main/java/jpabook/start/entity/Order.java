package jpabook.start.entity;

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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jpabook.start.entity.superClass.BaseEntity;

@Entity(name = "CH14_ORDERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@NamedEntityGraphs({
	@NamedEntityGraph(
		// 엔티티 그래프의 이름 정의
		name = "Order.withMember"
		// 함께 조회할 속성 선택.
		, attributeNodes = {
			@NamedAttributeNode("member")
		}
	)
	/*
	 * Order -> OrderItem -> Item까지 조회.
	 * Order -> OrderItem은 Order가 관리하지만
	 * OrderItem -> Item은 Order가 관리하는 필드가 아니다.
	 * 이럴 경우에는 subgraph를 사용하면 된다.
	 */
	, @NamedEntityGraph(
		name = "Order.withAll"
		, attributeNodes = {
			@NamedAttributeNode("member")
			, @NamedAttributeNode(value = "orderItems", subgraph = "orderItmes")
		}
		, subgraphs = {
			@NamedSubgraph(name = "orderItmes", attributeNodes = {
				@NamedAttributeNode("item")
			})
		}
	)
})

public class Order extends BaseEntity {

	@Id @GeneratedValue
	@Column(name = "ORDER_ID")
	private Long orderId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
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
