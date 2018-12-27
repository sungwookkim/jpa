package jpabook.start.manytomany.primarykey.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@SequenceGenerator(
	name = "CH06_MANY_TO_MANY_PRIMARYKEY_ORDERS_SEQ_GENRATOR"
	, sequenceName = "CH06_MANY_TO_MANY_PRIMARYKEY_ORDERS_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity(name = "CH06_MANY_TO_MANY_PRIMARYKEY_ORDERS")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH06_MANY_TO_MANY_PRIMARYKEY_ORDERS_SEQ")
	@Column(name = "ORDERS_ID")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;
	
	private int orderAmount;
	
	public Orders() { }
	
	public Orders(int orderAmount) {
		this.orderAmount = orderAmount;		
	}

	public Orders(Member member, Product product, int orderAmount) {
		this.member = member;
		this.product = product;
		this.orderAmount = orderAmount;
	}
	
	public long getId() { return this.id; }

	public Member getMember() { return this.member; }
	public void setMember(Member member) {	
		this.member = member;		
		
		if(!member.getOrders().contains(this)) {
			member.getOrders().add(this);
		}
	}
	
	public Product getProduct() { return this.product; }
	public void setProduct(Product product) { this.product = product; }
	
	public int getOrderAmount() { return this.orderAmount; }
	public void setOrderAmount(int orderAmount) { this.orderAmount = orderAmount; }
		
}
