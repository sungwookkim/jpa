package jpabook.start.access.entity;

import java.util.Optional;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity(name = "ACCESS_COMPLEX_MEMBER")
public class AccessComplexMember {

	@Id
	private String id;
	
	@Transient
	private String firstName;
	
	@Transient
	private String lastName;
	
	@Transient
	private String fullName;
	
	public AccessComplexMember() { }

	public String getId() { return this.id; }
	public void setId(String id) { this.id = id; }
	
	public String getFirstName() { return this.firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	
	public String getLastName() { return this.lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	
	@Access(AccessType.PROPERTY)
	public String getFullName() { return this.firstName + this.lastName; }
	protected void setFullName(String fullName) { this.fullName = fullName;}
	
	@Override
	public String toString() {
		return "{"
			+ "'id' : '" + Optional.ofNullable(this.id).orElse("") + "'"
			+ ", 'firstName' : '" + Optional.ofNullable(this.firstName).orElse("") + "'" 
			+ ", 'lastName' : '" + Optional.ofNullable(this.lastName).orElse("") + "'"
			+ ", 'fullName' : '" + Optional.ofNullable(this.fullName).orElse(this.firstName + this.lastName) + "'"
			+ "}";
	}
}
