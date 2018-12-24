package jpabook.start.unidirectional.manytoone.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "MANY_TO_ONE_TEAM")
public class Team {

	@Id
	@Column(name = "TEAM_ID")
	private String id;
	private String name;
	
	public Team() {}
	public Team(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	@Override
	public String toString() {
		return "{" 
			+ "'id' : '" + Optional.ofNullable(this.id).orElse("") + "'"
			+ ", 'name' : '" + Optional.ofNullable(this.name).orElse("") + "'"
			+ "}";
	}
}
