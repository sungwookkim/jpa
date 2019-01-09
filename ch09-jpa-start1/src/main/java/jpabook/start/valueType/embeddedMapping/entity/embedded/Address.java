package jpabook.start.valueType.embeddedMapping.entity.embedded;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Address {

	String street;
	String city;
	String state;
	
	@Embedded 
	ZipCode zipCode;
	
	public Address() { }
	public Address(String street, String city, String state, ZipCode zipCode) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}
	
	public String getStreet() { return street; }
	
	public String getCity() { return city; }
	
	public String getState() { return state; }
	
	public ZipCode getZipCode() { return zipCode; }
}


