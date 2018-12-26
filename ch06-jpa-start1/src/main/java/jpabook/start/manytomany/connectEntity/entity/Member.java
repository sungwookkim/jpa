package jpabook.start.manytomany.connectEntity.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@SequenceGenerator(
	name = "CH06_MANY_TO_MANY_CONNECT_MEMBER_SEQ_GENRATOR"
	, sequenceName = "CH06_MANY_TO_MANY_CONNECT_MEMBER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity(name = "CH06_MANY_TO_MANY_CONNECT_MEMBER")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH06_MANY_TO_MANY_CONNECT_MEMBER_SEQ")
	@Column(name = "MEMBER_ID")
	private long id;
	
	@OneToMany(mappedBy = "member")
	private List<MemberProduct> memberProducts = new ArrayList<>();
	
	private String userId;
	private String userName;
	
	public Member() { }
	
	public Member(String userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}
	
	public long getId() { return this.id; }
	
	public String getUserId() { return this.userId; }
	public void setUserId(String userId) { this.userId = userId; }
	
	public String getUserName() { return this.userName; }
	public void setUserName(String userName) { this.userName = userName; }
	
	public List<MemberProduct> getMemberProduct() { return this.memberProducts; }
	public void addMemberProduct(MemberProduct memberProduct) {
		if(!this.memberProducts.contains(memberProduct)) {
			this.memberProducts.add(memberProduct);
		}
		
		if(memberProduct.getMember() != this) {
			memberProduct.setMember(this);	
		}
	}
}
