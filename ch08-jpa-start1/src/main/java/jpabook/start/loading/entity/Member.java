package jpabook.start.loading.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH08_LOADING_MEMBER")
@SequenceGenerator(
	name = "CH08_LOADING_MEMBER_SEQUENCE"
	, sequenceName = "CH08_LOADING_MEMBER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH08_LOADING_MEMBER_SEQ")
	@Column(name = "MEMBER_ID")
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEAM_ID")
	private Team team;
	
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	private List<Order> order = new ArrayList<Order>();
	
	private String memberId;
	
	private String memberName;
	
	public Member() { }
	
	public Member(String memberId, String memberName) {
		this.memberId = memberId;
		this.memberName = memberName;
	}

	public long getId() { return id; }

	public String getMemberId() { return memberId; }
	public void setMemberId(String memberId) { this.memberId = memberId; }

	public String getMemberName() { return memberName; }
	public void setMemberName(String memberName) { this.memberName = memberName; }

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
	public void addOrders(Order order) {
		if(!this.order.contains(order)) {
			this.order.add(order);
		}
		
		if(order.getMember() != this) {
			order.setMember(this);
		}
	}	
}
