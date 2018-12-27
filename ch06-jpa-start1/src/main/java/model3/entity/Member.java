package model3.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name = "CH06_MODEL3_MEMBER")
@SequenceGenerator(
	name = "CH06_MODEL3_MEMBER_SEQ_GENRATOR"
	, sequenceName = "CH06_MODEL3_MEMBER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Member {

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH06_MODEL3_MEMBER_SEQ")
	@Column(name = "MEMBER_ID")
	private Long memberId;	
	private String userId;
	private String name;
	private String city;
	private String street;
	private String zipcode;
	
	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();
	
	public Member() { }
	
	public Member(String name, String userId, String city, String street, String zipcode) {
		this.name = name;
		this.userId = userId;
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
	
	public Long getMemberId() { return memberId; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getUserId() { return userId; }
	public void setUserId(String userId) { this.userId = userId; }

	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; }
	
	public String getStreet() { return street; }
	public void setStreet(String street) { this.street = street; }
	
	public String getZipcode() { return zipcode; }
	public void setZipcode(String zipcode) { this.zipcode = zipcode; }
	
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
