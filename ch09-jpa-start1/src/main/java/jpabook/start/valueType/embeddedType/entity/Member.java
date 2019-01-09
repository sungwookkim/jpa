package jpabook.start.valueType.embeddedType.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import jpabook.start.valueType.embeddedType.entity.embedded.Address;
import jpabook.start.valueType.embeddedType.entity.embedded.Period;

@Entity(name = "CH09_EMBEDDEDTYPE_MEMBER")
@SequenceGenerator(
	name = "CH09_EMBEDDEDTYPE_MEMBER_SEQUENCE"
	, sequenceName = "CH09_EMBEDDEDTYPE_MEMBER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH09_EMBEDDEDTYPE_MEMBER_SEQ")
	@Column(name = "MEMBER_ID")
	private long id;
	
	private String name;
	
	@Embedded 
	Period period;
	
	@Embedded
	Address address;
	
	public Member() { }
	
	public Member(String name, Period period, Address address) {
		this.name = name;
		this.period = period;
		this.address = address;
	}

	public long getId() { return id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Period getPeriod() { return period; }
	public void setPeriod(Period period) { this.period = period; }

	public Address getAddress() { return address; }
	public void setAddress(Address address) { this.address = address; }
}
