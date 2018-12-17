package jpabook.start.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER")
public class Member {
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "NAME")
	private String username;

	private Integer age;
	
	public String getId() { return this.id; }
	public void setId(String id) { this.id = id; }
	
	public String getUsername() { return this.username; }
	public void setUsername(String username) { this.username = username; }
	
	public Integer getAge() { return this.age; }
	public void setAge(Integer age) { this.age = age; }
	
	@Override
	public String toString() {
		return "{"
			+ "'id' : " + this.id
			+ ", 'username' : " + this.username
			+ ", 'age' : " + this.age
			+ "}";
	}
}
