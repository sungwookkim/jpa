package model2.entity;

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

@Entity(name = "CH05_MODEL2_MEMBER")
@SequenceGenerator(
	name = "CH05_MODEL2_MEMBER_SEQ_GENRATOR"
	, sequenceName = "CH05_MODEL2_MEMBER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Member {

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH05_MODEL2_MEMBER_SEQ")
	@Column(name = "MEMBER_ID")
	private Long memberId;	
	private String name;
	private String city;
	private String street;
	private String zipcode;

	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();
	
	public Member() { }
	public Member(String name, String city, String street, String zipcode) {
		this.name = name;
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
	
	public Long getMemberId() { return memberId; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; }
	
	public String getStreet() { return street; }
	public void setStreet(String street) { this.street = street; }
	
	public String getZipcode() { return zipcode; }
	public void setZipcode(String zipcode) { this.zipcode = zipcode; }
	
	public List<Order> getOrder() { return this.orders; }
	public void setOrder(List<Order> orders) { this.orders = orders; }
	public void addOrder(Order order) {
		this.orders.add(order); 
		order.setMember(this);
	}
	
}
