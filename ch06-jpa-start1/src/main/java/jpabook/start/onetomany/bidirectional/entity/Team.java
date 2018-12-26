package jpabook.start.onetomany.bidirectional.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@SequenceGenerator(
	name = "CH06_ONE_TO_MANY_BI_TEAM_SEQ_GENRATOR"
	, sequenceName = "CH06_ONE_TO_MANY_BI_TEAM_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity(name = "CH06_ONE_TO_MANY_BI_TEAM")
public class Team {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH06_ONE_TO_MANY_BI_TEAM_SEQ")
	@Column(name = "TEAM_ID")
	private Long id;
	private String name;
	
	@OneToMany
	@JoinColumn(name = "TEAM_ID")
	private List<Member> members = new ArrayList<Member>();	
	
	public Team() {}
	public Team(String name) {
		this.name = name;
	}
	
	public Long getId() { return id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public List<Member> getMembers() { return this.members; }
	public void addMember(Member member) {
		if(!this.members.contains(member)) {
			this.members.add(member);
		}

		if(member.getTeam() != this) {
			member.setTeam(this);
		}
	}
	
}
