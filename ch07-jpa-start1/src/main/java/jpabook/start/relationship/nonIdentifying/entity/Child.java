package jpabook.start.relationship.nonIdentifying.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH07_NONIDENTIFYING_CHILD")
@SequenceGenerator(
	name = "CH07_NONIDENTIFYING_CHILD_SEQUENCE"
	, sequenceName = "CH07_NONIDENTIFYING_CHILD_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Child {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH07_NONIDENTIFYING_CHILD_SEQ")
	@Column(name = "CHILD_ID")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private Parent parent;
	
	private String childId;
	
	private String name;
	
	public Child() { }
	
	public Child(String childId, String name) {
		this.childId = childId;
		this.name = name;
	}
	
	public Child(Parent parent, String childId, String name) {
		this.parent = parent;
		this.childId = childId;
		this.name = name;
	}

	public long getId() { return id; }

	public Parent getParent() { return parent; }
	public void setParent(Parent parent) { this.parent = parent; }

	public String getChildId() { return childId; }
	public void setChildId(String childId) { this.childId = childId; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }	
}
