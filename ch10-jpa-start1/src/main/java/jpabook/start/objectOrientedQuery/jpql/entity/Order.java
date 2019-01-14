package jpabook.start.objectOrientedQuery.jpql.entity;

import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import jpabook.start.objectOrientedQuery.jpql.entity.embedded.Address;

@Entity(name = "CH10_OOQ_ORDER")
@SequenceGenerator(
	name = "CH10_OOQ_ORDER_SEQUENCE"
	, sequenceName = "CH10_OOQ_ORDER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH10_OOQ_ORDER_SEQ")
	@Column(name = "ORDER_ID")
	private long id;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;
	
	@Embedded
	private Address address;
	
	private int orderAmount;
	
	public Order() { }
	
	public Order(Address address, int orderAmount) {
		this.address = address;
		this.orderAmount = orderAmount;
	}

	public long getId() { return id; }

	public Member getMember() { return member; }
	public void setMember(Member member) {
		Optional.ofNullable(this.member)
			.ifPresent(m -> m.getOrder().remove(this));
				
		this.member = member;
		
		if(!member.getOrder().contains(this)) {
			member.getOrder().add(this);
		}
	}

	public Product getProduct() { return product; }
	public void setProduct(Product product) {
		Optional.ofNullable(this.product)
			.ifPresent(p -> p.getOrder().remove(this));
		
		this.product = product;
		
		if(!product.getOrder().contains(this)) {
			product.getOrder().add(this);
		}
	}
	
	public Address getAddress() { return address; }
	public void setAddress(Address address) { this.address = address; }

	public int getOrderAmount() { return orderAmount; }
	public void setOrderAmount(int orderAmount) { this.orderAmount = orderAmount; }
	
	
}
