package jpabook.start.bidirectional.onetomany.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name = "ONE_TO_MANY_TEAM")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Team {

	@Id
	@Column(name = "TEAM_ID")
	private String teamId;
	private String name;

	@OneToMany(mappedBy = "team")
	private List<Member> members = new ArrayList<>();
	
	public Team() {}
	public Team(String teamId, String name) {
		this.teamId = teamId;
		this.name = name;
	}
	
	public String getId() { return teamId; }
	public void setId(String teamId) { this.teamId = teamId; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public List<Member> getMember() { return this.members; }
	public void setMember(List<Member> members) { this.members = members; }
}
