package jpabook.start.relationship.compositeKey.identifying.embeddedId.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH07_COMPOSITEKEY_IDENTIFYING_EMBEDDEBID_PARENT")
@SequenceGenerator(
	name = "CH07_COMPOSITEKEY_IDENTIFYING_EMBEDDEBID_PARENT_SEQUENCE"
	, sequenceName = "CH07_COMPOSITEKEY_IDENTIFYING_EMBEDDEBID_PARENT_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Parent {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH07_COMPOSITEKEY_IDENTIFYING_EMBEDDEBID_PARENT_SEQ")
	@Column(name = "PARENT_ID")
	private long id;
	
	private String memberId;
	
	private String memberName;
	
	public Parent() { }
	
	public Parent(String memberId, String memberName) {
		this.memberId = memberId;
		this.memberName = memberName;
	}

	public long getId() { return id; }

	public String getMemberId() { return memberId; }
	public void setMemberId(String memberId) { this.memberId = memberId; }

	public String getMemberName() { return memberName; }
	public void setMemberName(String memberName) { this.memberName = memberName; }
}
