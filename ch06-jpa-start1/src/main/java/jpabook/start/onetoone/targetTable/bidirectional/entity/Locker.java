package jpabook.start.onetoone.targetTable.bidirectional.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@SequenceGenerator(
	name = "CH06_ONE_TO_ONE_TARGET_BI_LOCKER_SEQ_GENRATOR"
	, sequenceName = "CH06_ONE_TO_ONE_TARGET_BI_LOCKER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity(name = "CH06_ONE_TO_ONE_TARGET_BI_LOCKER")
public class Locker {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH06_ONE_TO_ONE_TARGET_BI_LOCKER_SEQ")
	@Column(name = "LOCKER_ID")
	private Long id;
	
	private String name;
	
	@OneToOne
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	public Locker() { }
	
	public Locker(String name) {
		this.name = name;
	}
	
	public Locker(String name, Member member) {
		this.name = name;
		this.member = member;
	}

	public Long getId() { return this.id; }
	
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }
	
	public Member getMember() { return this.member; }
	public void setMember(Member member) {
		this.member = member;

		if(member.getLocker() != this) {
			member.setLocker(this);	
		}
		
	}
}
