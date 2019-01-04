package jpabook.start.relationship.compositeKey.identifying.embeddedId.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity(name = "CH07_COMPOSITEKEY_IDENTIFYING_EMBEDDEBID_CHILD")
public class Child {

	@EmbeddedId
	private ChildId id;
	
	@MapsId("parentId")
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private Parent parent;

	private String name;
	
	public Child() { }
	
	public Child(Parent parent, ChildId childId, String name) {
		this.parent = parent;
		this.id = childId;
		this.name = name;
	}
	
	public ChildId getId() { return id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Parent getParent() { return parent; }
	public void setParent(Parent parent) { this.parent = parent; }
}
