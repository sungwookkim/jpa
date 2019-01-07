package jpabook.start.joinTable.manytoone.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH07_JOINTABLE_MANYTOONE_CHILD")
@SequenceGenerator(
	name = "CH07_JOINTABLE_MANYTOONE_CHILD_SEQUENCE"
	, sequenceName = "CH07_JOINTABLE_MANYTOONE_CHILD_SEQ"
	, initialValue = 1
	, allocationSize = 1	
)
public class Child {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH07_JOINTABLE_MANYTOONE_CHILD_SEQ")
	@Column(name = "CHILD_ID")
	private long id;
	
	@ManyToOne(optional = false)
	/*
	 * @JoinColumn 대신 @JoinTable을 사용했다.
	 * @JoinColumn 속성은 다음과 같다.
	 * name : 매핑할 조인 테이블 이름
	 * joinColumns : 현재 엔티티를 참조하는 외래 키
	 * inverseJoinColumns : 반대방향 엔티티를 참조하는 외래 키
	 */
	@JoinTable(
		name = "CH07_JOINTABLE_MANYTOONE"
		, joinColumns = @JoinColumn(name = "CHILD_ID")
		, inverseJoinColumns = @JoinColumn(name = "PARENT_ID")
	)
	private Parent parent;
	
	private String name;
	
	public Child() { }
	
	public Child(String name) {
		this.name = name;
	}

	public Child(Parent parent, String name) {
		this.parent = parent;
		this.name = name;
	}
	public long getId() { return id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Parent getParent() { return parent; }
	public void setParent(Parent parent) {
		Optional.ofNullable(this.parent)
			.ifPresent(p -> p.getChild().remove(this));
		
		this.parent = parent;
		
		if(!parent.getChild().contains(this)) {
			parent.getChild().add(this);
		}
	}
}
