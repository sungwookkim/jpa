package jpabook.start.valueType.embeddedMapping.entity.embedded;

import javax.persistence.Embeddable;

@Embeddable
public class ZipCode {

	String zip;
	String plusFour;
	
	public ZipCode() { }
	
	public ZipCode(String zip, String plusFour) {
		this.zip = zip;
		this.plusFour = plusFour;
	}

	public String getZip() { return zip; }

	public String getPlusFour() { return plusFour; }
}
