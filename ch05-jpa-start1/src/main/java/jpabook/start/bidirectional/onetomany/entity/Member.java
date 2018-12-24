package jpabook.start.bidirectional.onetomany.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name = "ONE_TO_MANY_MEMBER")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Member {
	
	@Id
	@Column(name = "MEMBER_ID")
	private String memberId;	
	private String username;
	
	@ManyToOne
	@JoinColumn(name = "TEAM_ID")
	private Team team;
	
	public Member() { }
	public Member(String memberId, String username, Team team) {
		this.memberId = memberId;
		this.username = username;
		this.team = Optional.ofNullable(team).orElse(new Team());
	}
	
	public String getId() { return memberId; }
	public void setId(String memberId) { this.memberId = memberId; }

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	
	public Team getTeam() { return this.team; }
	public void setTeam(Team team) {
		Optional.ofNullable(this.team)
			.ifPresent(t -> {
				t.getMember().remove(this);
			});
		
		this.team = team;
		team.getMember().add(this);
	}
	
}
