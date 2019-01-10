package model6.entity.embedded;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
	
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
	
	@Override
	public boolean equals(Object obj) {
		Address address = (Address) obj;
		
		if(this.city.equals(address.getCity())
			&& this.street.equals(address.getStreet())
			&& this.zipCode.equals(address.getZipCode()) ) {
			return true;
		}
		
		return false;
	}
	
}
