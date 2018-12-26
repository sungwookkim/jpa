package jpabook.start.manytomany.connectEntity.entity;

import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@IdClass(MemberProductId.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity(name = "CH06_MANY_TO_MANY_CONNECT_MEMBER_PRODUCT")
public class MemberProduct {

	@Id
	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	private Member member;	
	
	@Id
	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;
	
	private int orderAmount;
	
	public MemberProduct() {};
	
	public MemberProduct(int orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	public int getOrderAmount() { return this.orderAmount; }
	public void setOrderAmount(int orderAmount) { this.orderAmount = orderAmount; }
	
	public Member getMember() { return this.member; }
	public void setMember(Member member) {
		Optional.ofNullable(this.member)
			.ifPresent(m -> {
				member.getMemberProduct().remove(this);
			});
		
		this.member = member;
		member.getMemberProduct().add(this);
	}

	public Product getProduct() { return this.product; }
	public void setProduct(Product product) {
		this.product = product;
	}
}
