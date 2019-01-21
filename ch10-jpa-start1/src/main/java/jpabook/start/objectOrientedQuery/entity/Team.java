package jpabook.start.objectOrientedQuery.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH10_OOQ_TEAM")
@SequenceGenerator(
	name = "CH10_OOQ_TEAM_SEQUENCE"
	, sequenceName = "CH10_OOQ_TEAM_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Team {

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH10_OOQ_TEAM_SEQ")
	@Column(name = "TEAM_ID")
	private long id;
	
	@OneToMany(mappedBy="team", cascade = CascadeType.ALL)
	private List<Member> member = new ArrayList<>();
	
	private String name;
	
	public Team() { }
	
	public Team(String name) {
		this.name = name;
	}

	public long getId() { return id; }

	public List<Member> getMember() { return member; }
	public void addMember(Member member) {
		if(!this.member.contains(member)) {
			this.member.add(member);
		}
		
		if(member.getTeam() != this) {
			member.setTeam(this);
		}
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	
}
