package jpabook.start.valueType.embeddedMapping.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "CH09_EMBEDDEDMAPPING_PHONESERVICEPROVIDER")
public class PhoneServiceProvider {

	@Id
	private String name;
	
	public PhoneServiceProvider() { }
	
	public PhoneServiceProvider(String name) {
		this.name = name;
	}

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }
}
