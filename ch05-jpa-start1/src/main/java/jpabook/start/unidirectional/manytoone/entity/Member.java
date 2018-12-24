package jpabook.start.unidirectional.manytoone.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "MANY_TO_ONE_MEMBER")
public class Member {
	
	@Id
	@Column(name = "MEMBER_ID")
	private String id;	
	private String username;
	
	@ManyToOne
	@JoinColumn(name = "TEAM_ID")
	private Team team;

	public Member() { }
	public Member(String id, String username, Team team) {
		this.id = id;
		this.username = username;
		this.team = Optional.ofNullable(team).orElse(new Team()) ;
	}
	
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	
	public Team getTeam() { return team; }
	public void setTeam(Team team) { this.team = team; }
	
	@Override
	public String toString() {
		return "{" 
			+ "'id' : '" + Optional.ofNullable(this.id).orElse("") + "'"
			+ ", 'username' : '" + Optional.ofNullable(this.username).orElse("") + "'"
			+ ", 'team' : " + Optional.ofNullable(this.team).map(Team::toString).orElse("''")
			+ "}";
	}
}
