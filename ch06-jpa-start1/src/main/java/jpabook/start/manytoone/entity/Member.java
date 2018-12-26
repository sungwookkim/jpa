package jpabook.start.manytoone.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@SequenceGenerator(
	name = "CH06_MANY_TO_ONE_MEMBER_SEQ_GENRATOR"
	, sequenceName = "CH06_MANY_TO_ONE_MEMBER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity(name = "CH06_MANY_TO_ONE_MEMBER")
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH06_MANY_TO_ONE_MEMBER_SEQ")
	@Column(name = "MEMBER_ID")
	private Long id;	
	private String memberId;
	private String username;
	
	@ManyToOne
	@JoinColumn(name = "TEAM_ID")
	private Team team;

	public Member() { }
	
	public Member(String memberId, String username) {
		this.memberId = memberId;
		this.username = username;
	}
	
	public Member(String memberId, String username, Team team) {
		this.memberId = memberId;
		this.username = username;
		this.team = Optional.ofNullable(team).orElse(new Team()) ;
	}
	
	public Long getId() { return id; }	

	public String getMemberId() { return this.memberId; }
	public void setMemberId(String memberId) { this.memberId = memberId; }

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	
	public Team getTeam() { return team; }
	public void setTeam(Team team) {
		Optional.ofNullable(this.team)
			.ifPresent(t -> {
				t.getMembers().remove(this);
			});

		this.team = team; 
		
		// 무한루프에 빠지지 않도록 체크. 
		if(!team.getMembers().contains(this)) { 
			team.getMembers().add(this); 
		}
	}
}
