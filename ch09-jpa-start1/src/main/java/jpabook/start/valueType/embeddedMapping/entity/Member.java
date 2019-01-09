package jpabook.start.valueType.embeddedMapping.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import jpabook.start.valueType.embeddedMapping.entity.embedded.Address;
import jpabook.start.valueType.embeddedMapping.entity.embedded.PhoneNumber;

@Entity(name = "CH09_EMBEDDEDMAPPING_MEMBER")
@SequenceGenerator(
	name = "CH09_EMBEDDEDMAPPING_MEMBER_SEQUENCE"
	, sequenceName = "CH09_EMBEDDEDMAPPING_MEMBER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH09_EMBEDDEDMAPPING_MEMBER_SEQ")	
	@Column(name = "MEMBER_ID")
	private long id;
	
	@Embedded
	private Address address;
	
	@Embedded
	private PhoneNumber phoneNumber;
	
	public Member() { }
	
	public Member(Address address, PhoneNumber phoneNumber) {
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	public long getId() { return id; }

	public Address getAddress() { return address; }
	public void setAddress(Address address) { this.address = address; }

	public PhoneNumber getPhoneNumber() { return phoneNumber; }
	public void setPhoneNumber(PhoneNumber phoneNumber) { this.phoneNumber = phoneNumber; }

}
