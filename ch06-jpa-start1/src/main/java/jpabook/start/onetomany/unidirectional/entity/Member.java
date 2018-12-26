package jpabook.start.onetomany.unidirectional.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@SequenceGenerator(
	name = "CH06_ONE_TO_MANY_UNI_MEMBER_SEQ_GENRATOR"
	, sequenceName = "CH06_ONE_TO_MANY_UNI_MEMBER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity(name = "CH06_ONE_TO_MANY_UNI_MEMBER")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH06_ONE_TO_MANY_UNI_MEMBER_SEQ")
	@Column(name = "MEMBER_ID")
	private Long id;	
	private String memberId;
	private String username;

	public Member() { }
	
	public Member(String memberId, String username) {
		this.memberId = memberId;
		this.username = username;
	}

	public Long getId() { return id; }	

	public String getMemberId() { return this.memberId; }
	public void setMemberId(String memberId) { this.memberId = memberId; }

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
}
