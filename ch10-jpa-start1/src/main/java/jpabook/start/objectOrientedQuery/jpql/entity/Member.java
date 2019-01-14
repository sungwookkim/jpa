package jpabook.start.objectOrientedQuery.jpql.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH10_OOQ_MEMBER")
@SequenceGenerator(
	name = "CH10_OOQ_MEMBER_SEQUENCE"
	, sequenceName = "CH10_OOQ_MEMBER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
@NamedQuery(
	name = "CH10_OOQ_MEMBER.findByUserName"
	, query = "SELECT m FROM CH10_OOQ_MEMBER m WHERE m.userName = :userName"
)
@NamedQueries({
	@NamedQuery(
		name = "CH10_OOQ_MEMBER.count"
		, query = "SELECT COUNT(m) FROM CH10_OOQ_MEMBER m"
	)
	, @NamedQuery(
		name = "CH10_OOQ_MEMBER.findByUserId"
		, query = "SELECT m FROM CH10_OOQ_MEMBER m WHERE m.id = :id"
	)
})
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "CH10_OOQ_MEMBER_SEQ")
	@Column(name = "MEMBER_ID")
	private long id;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "TEAM_ID", nullable = false)
	private Team team;
	
	@OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<Order> order = new ArrayList<>();
	
	private String userName;
	
	private int age;
	
	public Member() { }
	
	public Member(String userName, int age) {
		this.userName = userName;
		this.age = age;
	}

	public long getId() { return id; }

	public Team getTeam() { return team; }
	public void setTeam(Team team) {
		Optional.ofNullable(this.team)	
			.ifPresent(t -> t.getMember().remove(this));
		
		this.team = team;
		
		if(!team.getMember().contains(this)) {
			team.getMember().add(this);
		}
	}
	
	public List<Order> getOrder() { return order; }
	public void addOrder(Order order) {
		if(!this.order.contains(order)) {
			this.order.add(order);
		}
		
		if(order.getMember() != this) {
			order.setMember(this);
		}
	}	

	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }

	public int getAge() { return age; }
	public void setAge(int age) { this.age = age; }
	
	
}
