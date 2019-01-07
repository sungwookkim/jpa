package jpabook.start.relationship.nonIdentifying.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH07_NONIDENTIFYING_PARENT")
@SequenceGenerator(
	name = "CH07_NONIDENTIFYING_PARENT_SEQUENCE"
	, sequenceName = "CH07_NONIDENTIFYING_PARENT_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Parent {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH07_NONIDENTIFYING_PARENT_SEQ")
	@Column(name = "PARENT_ID")
	private long id;
	
	private String parentId;
	
	private String name;
	
	public Parent() { }
	
	public Parent(String parentId, String name) {
		this.parentId = parentId;
		this.name = name;
	}

	public long getId() { return id; }

	public String getParentId() { return parentId; }
	public void setParentId(String parentId) { this.parentId = parentId; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}
