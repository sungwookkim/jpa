package jpabook.start.valueType.elementCollection.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;

import jpabook.start.valueType.elementCollection.entity.embedded.Address;

/*
 * 관계형 데이터베이스의 테이블은 컬럼 안에 컬렉션을 포함할 수 없다. 따라서 별도의 테이블을 추가하고
 * @CollectionTable를 사용해서 추가한 테이블을 매핑해야 한다.
 */
@Entity(name = "CH09_ELEMENTCOLLECTION_MEMBER")
@SequenceGenerator(
	name = "CH09_ELEMENTCOLLECTION_MEMBER_SEQUENCE"
	, sequenceName = "CH09_ELEMENTCOLLECTION_MEMBER_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH09_ELEMENTCOLLECTION_MEMBER_SEQ")
	@Column(name = "MEMBER_ID")
	private long id;
	
	@Embedded
	private Address address;
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(
		name = "CH09_ELEMENTCOLLECTION_FAVORITE_FOODS"
		, joinColumns = @JoinColumn(name = "MEMBER_ID")
	)
	/*
	 * 값으로 사용되는 컬럼이 하나면 @Column을 사용해서 컬럼명을 지정할 수 있다.
	 */
	@Column(name = "FOOD_NAME")
	private Set<String> favoriteFoods = new HashSet<>();
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(
		name = "CH09_ELEMENTCOLLECTION_ADDRESS"
		, joinColumns = @JoinColumn(name = "MEMBER_ID")
	)
	private List<Address> addresses = new ArrayList<>(); 
	
	public Member() { }
	
	public Member(Address address, Set<String> favoriteFoods, List<Address> addresses) {
		this.address = address;
		this.favoriteFoods = favoriteFoods;
		this.addresses = addresses;
	}

	public long getId() { return id; }
	
	public Address getAddress() { return address; }
	public void setAddress(Address address) { this.address = address; }

	public Set<String> getFavoriteFoods() { return favoriteFoods; }
	public void addFavoriteFoods(String favoriteFoods) { this.favoriteFoods.add(favoriteFoods); }

	public List<Address> getAddresses() { return addresses; }
	public void addAddresses(Address addresses) { this.addresses.add(addresses); }
}
