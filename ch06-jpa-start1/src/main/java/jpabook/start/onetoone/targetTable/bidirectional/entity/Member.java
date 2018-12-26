package jpabook.start.onetoone.targetTable.bidirectional.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@SequenceGenerator(
	name = "CH06_ONE_TO_ONE_TARGET_BI_MEMBER_SEQ_GENRATOR"
	, sequenceName = "CH06_ONE_TO_ONE_TARGET_BI_MEMBER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity(name = "CH06_ONE_TO_ONE_TARGET_BI_MEMBER")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH06_ONE_TO_ONE_TARGET_BI_MEMBER_SEQ")
	@Column(name = "MEMBER_ID")
	private Long id;
	
	@OneToOne(mappedBy = "member")	
	Locker locker;
	
	@Column(name = "USER_ID")
	private String userId;
	
	public Member() {};

	public Member(String userId) {
		this.userId = userId;
	}
	
	public Member(String userId, Locker locker) {
		this.userId = userId;
		this.locker = locker;
	} 
	
	public Long getId() { return this.id; }
	
	public String getUserId() { return this.userId; }
	public void setUserId(String userId) { this.userId = userId; }
	
	public Locker getLocker() { return this.locker; }
	public void setLocker(Locker locker) { 
		this.locker = locker;
		
		if(locker.getMember() != this) {
			locker.setMember(this);
		}
	}	
}
