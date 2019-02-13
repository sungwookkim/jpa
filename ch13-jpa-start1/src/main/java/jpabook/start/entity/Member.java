package jpabook.start.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jpabook.start.entity.embedded.Address;
import jpabook.start.entity.superClass.BaseEntity;

@Entity(name = "CH14_MEMBER")
@SequenceGenerator(
	name = "CH14_MEMBER_SEQ_GENRATOR"
	, sequenceName = "CH14_MEMBER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Member extends BaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH14_MEMBER_SEQ")
	@Column(name = "MEMBER_ID")
	private Long memberId;	
	private String userId;
	private String name;
	
	@Embedded Address address;
	
	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();
	
	public Member() { }
	
	public Member(String name, String userId, Address address) {
		this.name = name;
		this.userId = userId;
		this.address = address;
	}
	
	public Long getMemberId() { return memberId; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }

	public Address getAddress() { return address; }
	public void setAddress(Address address) { this.address = address; }

	public List<Order> getOrder() { return this.orders; }
	public void addOrder(Order order) {
		if(!this.orders.contains(order)) {
			this.orders.add(order);
		}
		
		if(order.getMember() != this) {
			order.setMember(this);
		}
	}
	
}
