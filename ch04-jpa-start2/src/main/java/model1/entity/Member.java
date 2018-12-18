package model1.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH04_MODEL1_MEMBER")
@SequenceGenerator(
	name = "CH04_MODEL1_MEMBER_SEQ_GENRATOR"
	, sequenceName = "CH04_MODEL1_MEMBER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Member {

	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH04_MODEL1_MEMBER_SEQ")
	@Column(name = "MEMBER_ID")
	private Long id;	
	private String name;
	private String city;
	private String street;
	private String zipcode;
	
	public Member() { }
	public Member(String name, String city, String street, String zipcode) {
		this.name = name;
		this.city = city;
		this.street = street;
		this.zipcode = zipcode;
	}
	
	public Long getId() { return id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; }
	
	public String getStreet() { return street; }
	public void setStreet(String street) { this.street = street; }
	
	public String getZipcode() { return zipcode; }
	public void setZipcode(String zipcode) { this.zipcode = zipcode; }
	
	@Override
	public String toString() {
		return "{"
			+ "'id' : '" + Optional.ofNullable(this.id)
				.map(id -> String.valueOf(id)).orElse("") + "'"

			+ ", 'name' : '" + Optional.ofNullable(this.name).orElse("") + "'"
			+ ", 'city' : '" + Optional.ofNullable(this.city).orElse("") + "'"
			+ ", 'street' : '" + Optional.ofNullable(this.street).orElse("") + "'"
			+ ", 'zipcode' : '" + Optional.ofNullable(this.zipcode).orElse("") + "'"			
			+ "}";
	}
}
