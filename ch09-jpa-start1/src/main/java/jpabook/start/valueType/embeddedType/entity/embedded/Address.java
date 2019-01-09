package jpabook.start.valueType.embeddedType.entity.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

	@Column(name = "city")
	private String city;
	private String street;
	private String zipCode;
	
	public Address() { }

	public Address(String city, String street, String zipCode) {
		this.city = city;
		this.street = street;
		this.zipCode = zipCode;
	}
	
	public String getCity() { return city; }
	
	public String getStreet() { return street; }
	
	public String getZipCode() { return zipCode; }
}
