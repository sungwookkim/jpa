package jpabook.start.valueType.embeddedMapping.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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
	/*	
	 * 같은 임베디드 타입을 사용하게 되면 테이블에 매핑하는 컬럼명이 중복이된다.
	 * 이런 경우는 @AttributeOverrides을 이용해서 임베디드 타입에 정의한 매핑정보를 재정의를 해야 한다. 
	 */
	@AttributeOverrides({
		@AttributeOverride(name = "street", column = @Column(name = "COMPANY_STREET"))
		, @AttributeOverride(name = "city", column = @Column(name = "COMPANY_CITY"))
		, @AttributeOverride(name = "state", column = @Column(name = "COMPANY_STATE"))
		, @AttributeOverride(name = "zipCode.zip", column = @Column(name = "COMPANY_ZIP"))
		, @AttributeOverride(name = "zipCode.plusFour", column = @Column(name = "COMPANY_PLUSFOUR"))
	})
	private Address companyAddres;
	
	@Embedded
	private PhoneNumber phoneNumber;
	
	public Member() { }
	
	public Member(Address address, Address companyAddres, PhoneNumber phoneNumber) {
		this.address = address;
		this.companyAddres = companyAddres;
		this.phoneNumber = phoneNumber;
	}

	public long getId() { return id; }

	public Address getAddress() { return address; }
	public void setAddress(Address address) { this.address = address; }

	public Address getCompanyAddres() { return companyAddres; }
	public void setCompanyAddres(Address companyAddres) { this.companyAddres = companyAddres; }

	public PhoneNumber getPhoneNumber() { return phoneNumber; }
	public void setPhoneNumber(PhoneNumber phoneNumber) { this.phoneNumber = phoneNumber; }
}
