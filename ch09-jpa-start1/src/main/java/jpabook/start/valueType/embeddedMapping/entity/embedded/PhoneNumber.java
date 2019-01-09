package jpabook.start.valueType.embeddedMapping.entity.embedded;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import jpabook.start.valueType.embeddedMapping.entity.PhoneServiceProvider;

@Embeddable
public class PhoneNumber {

	String areaCode;
	String localNumber;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	PhoneServiceProvider phoneServiceProvider;
	
	public PhoneNumber() { }
	
	public PhoneNumber(String areaCode, String localNumber, PhoneServiceProvider phoneServiceProvider) {
		this.areaCode = areaCode;
		this.localNumber = localNumber;
		this.phoneServiceProvider = phoneServiceProvider;
	}

	public String getAreaCode() { return areaCode; }

	public String getLocalNumber() { return localNumber; }

	public PhoneServiceProvider getPhoneServiceProvider() { return phoneServiceProvider; }
}
