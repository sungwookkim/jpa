package jpabook.start.loading.entity;

import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH08_LOADING_ORDER")
@SequenceGenerator(
	name = "CH08_LOADING_ORDER_SEQUENCE"
	, sequenceName = "CH08_LOADING_ORDER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH08_LOADING_ORDER_SEQ")
	@Column(name = "ORDER_ID")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;
	
	private Date orderDate;
	
	public Order() { }
	
	public Order(Date orderDate) {
		this.orderDate = orderDate;
	}

	long getId() { return id; }
	
	Date getOrderDate() { return orderDate; }
	void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

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
}
