package jpabook.start.relationship.nonIdentifying.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH07_NONIDENTIFYING_GRANDCHILD")
@SequenceGenerator(
	name = "CH07_NONIDENTIFYING_GRANDCHILD_SEQUENCE"
	, sequenceName = "CH07_NONIDENTIFYING_GRANDCHILD_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class GrandChild {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH07_NONIDENTIFYING_GRANDCHILD_SEQ")
	@Column(name = "GRANDCHILD_ID")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "CHILD_ID")
	private Child child;
	
	private String grandChildId;
	
	private String name;
	
	public GrandChild() { }
	
	public GrandChild(Child child, String grandChildId, String name) {
		this.child = child;
		this.grandChildId = grandChildId;
		this.name = name;
	}
	
	public GrandChild(String grandChildId, String name) {
		this.grandChildId = grandChildId;
		this.name = name;
	}

	public long getId() { return id; }
	
	public Child getChild() { return child; }
	public void setChild(Child child) { this.child = child; }

	public String getGrandChildId() { return grandChildId; }
	public void setGrandChildId(String grandChildId) { this.grandChildId = grandChildId; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
}
