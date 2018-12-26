package jpabook.start.manytomany.bidirectional.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@SequenceGenerator(
	name = "CH06_MANY_TO_MANY_BI_MEMBER_SEQ_GENRATOR"
	, sequenceName = "CH06_MANY_TO_MANY_BI_MEMBER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity(name = "CH06_MANY_TO_MANY_BI_MEMBER")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH06_MANY_TO_MANY_BI_MEMBER_SEQ")
	@Column(name = "MEMBER_ID")
	private long id;
	
	@ManyToMany
	@JoinTable(
		// 연결 테이블을 지정한다. 여기서는 MEMBER_PRODUCT 테이블을 선택했다.
		name = "MANY_TO_MANY_BI_MEMBER_PRODUCT"
		// 현재 방향인 회원과 매핑할 조인 컬럼 정보를 지정한다.
		, joinColumns = @JoinColumn(name = "MEMBER_ID")
		// 반대 방향인 상품과 매핑할 조인 컬럼 정보를 지정한다.
		, inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
	private List<Product> products = new ArrayList<>();
	
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
	
	public List<Product> getProduct() { return this.products; }
	public void addProduct(Product product) {
		if(!this.products.contains(product)) {
			this.products.add(product);	
		}
		
		if(!product.getMember().contains(this)) {
			product.getMember().add(this);
		}
	}
	
	
}
