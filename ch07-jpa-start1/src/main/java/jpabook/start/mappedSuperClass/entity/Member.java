package jpabook.start.mappedSuperClass.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

import jpabook.start.mappedSuperClass.entity.abs.BaseEntity;

@Entity(name = "CH07_MAPPED_SUPER_CLASS_MEMBER")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "MEMBER_ID"))
	, @AttributeOverride(name = "name", column = @Column(name = "MEMBER_NAME"))	
})
public class Member extends BaseEntity {

	private String email;

	public Member() { }
	
	public Member(String name, String email) {
		this.setName(name);
		this.email = email;
	}

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	

}
