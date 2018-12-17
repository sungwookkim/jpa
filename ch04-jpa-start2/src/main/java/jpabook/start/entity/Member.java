package jpabook.start.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MEMBER")
public class Member {

	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "NAME")
	private String username;
	
	private int age;
	
	@Enumerated(EnumType.STRING)
	RoleType roleType;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;
	
	@Lob
	private String description;
	
	public Member() { }
	
	public String getId() { return this.id; }
	public void setId(String id) { this.id = id; }
	
	public String getUsername() { return this.username; }
	public void setUsername(String username) { this.username = username; }
	
	public int getAge() { return this.age; }
	public void setAge(int age) { this.age = age; }
	
	public RoleType getRoleType() { return this.roleType; }
	public void setRoleType(RoleType roleType) { this.roleType = roleType; }
	
	public Date getCreatedDate() { return this.createdDate; }
	public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
	
	public Date getLastModifiedDate() { return this.lastModifiedDate; }
	public void setLastModifiedDate(Date lastModifiedDate) { this.lastModifiedDate = lastModifiedDate; } 
	
	public String getDescription() { return this.description; }
	public void setDescription(String description) { this.description = description; }

	@Override
	public String toString() {
		return "{"
			+ "'id' : " + this.id
			+ ", 'username' : " + this.username
			+ ", 'age' : " + this.age
			+ ", 'roleType' : " + this.roleType
			+ ", 'createdDate' : " + this.createdDate
			+ ", 'lastModifiedDate' : " + this.lastModifiedDate
			+ ", 'description' : " + this.description
			+ "}";
	}
}
