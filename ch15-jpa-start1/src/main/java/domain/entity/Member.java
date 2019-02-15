package domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import domain.embedded.Address;

@Entity(name = "CH15_MEMBER")
@SequenceGenerator(
	name = "CH15_MEMBER_SEQUENCE"
	, sequenceName = "CH15_MEMBER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Member {

	@Id
	@Column(name = "MEMBER_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH15_MEMBER_SEQ")	
	private long id;
	
	private String name;
	
	@Embedded
	private Address address;
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<>();
	
	public Member() { }
	
	public Member(String name, Address address) {
		this.name = name;
		this.address = address;
	}

	public long getId() { return id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Address getAddress() { return address; }
	public void setAddress(Address address) { this.address = address; }

	public List<Order> getOrders() { return orders; }
	public void addOrders(Order order) {
		if(!this.orders.contains(order)) {
			this.orders.add(order);
		}
		
		if(order.getMember() != this) {
			order.setMember(this);
		}
	}
}
