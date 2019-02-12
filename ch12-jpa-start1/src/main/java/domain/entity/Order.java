package domain.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import domain.entity.status.DeliveryStatus;
import domain.entity.status.OrderStatus;

@Entity(name = "CH12_ORDER")
@SequenceGenerator(
	name = "CH12_ORDER_SEQUENCE"
	, sequenceName = "CH12_ORDER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Order {

	@Id
	@Column(name = "ORDER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH12_ORDER_SEQ")
	private long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "DELIVERY_ID")
	private Delivery delivery;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	private Date orderDate;
	
	public Order() { }

	public long getId() { return id; }

	public Member getMember() { return member; }
	public void setMember(Member member) {
		Optional.ofNullable(this.member)
			.ifPresent(m -> member.getOrders().remove(this));
		
		this.member = member;
		
		if(!member.getOrders().contains(this)) {
			member.getOrders().add(this);
		}
	}

	public List<OrderItem> getOrderItems() { return orderItems; }
	public void addOrderItems(OrderItem orderItem) {
		if(!this.orderItems.contains(orderItem)) {
			this.orderItems.add(orderItem);			
		}
		
		if(orderItem.getOrder() != this) {
			orderItem.setOrder(this);
		}
	}

	public Delivery getDelivery() { return delivery; }
	public void setDelivery(Delivery delivery) {		
		this.delivery = delivery;
		
		if(delivery.getOrder() != this) {
			delivery.setOrder(this);	
		}
	}
	
	public static Order createOrder(Member member, Delivery delivery, OrderItem orderItems) {
		return createOrder(member, delivery, Arrays.asList(orderItems));
	}
		
	public static Order createOrder(Member member, Delivery delivery, List<OrderItem> orderItems) {

		Order order = new Order();
		order.setMember(member);
		order.setDelivery(delivery);
		order.setStatus(OrderStatus.ORDER);
		order.setOrderDate(new Date());
		
		orderItems.stream().forEach(o -> {
			order.addOrderItems(o);
		});

		return order;
	}
	
	public void cancle() {
		Optional.ofNullable(delivery.getStatus())
			.filter(s -> s == DeliveryStatus.READY)
			.map(s -> {
				this.setStatus(OrderStatus.CANCEL);
				return s;
			})
			.orElseThrow(() -> {
				throw new RuntimeException("이미 배송완료된 상품은 취소가 불가능합니다.");
			});

		orderItems.stream().forEach(o -> {
			o.cancle();
		});
	}
	
	public void complete() {
		Optional.ofNullable(this.status)
			.filter(s -> s == OrderStatus.ORDER)
			.ifPresent(s -> delivery.setStatus(DeliveryStatus.COMP));
	}
	
	public int getTotalPrice() {
		return orderItems.stream()
			.collect(Collectors.reducing(0, OrderItem::getTotalPrice, Integer::sum));
	}
	
	public OrderStatus getStatus() { return status; }
	public void setStatus(OrderStatus status) { this.status = status; }
	
	public Date getOrderDate() { return orderDate; }
	public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
}
