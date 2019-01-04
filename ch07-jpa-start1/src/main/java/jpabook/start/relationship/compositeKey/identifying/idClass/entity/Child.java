package jpabook.start.relationship.compositeKey.identifying.idClass.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "CH07_COMPOSITEKEY_IDENTIFYING_IDCLASS_CHILD")
@IdClass(ChildId.class)
public class Child {

	@Id
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	public Parent parent;
	
	@Id
	@Column(name = "CHILD_ID")
	private String childId;
	
	private String childName;

	public Child() { }
	
	public Child(Parent parent, String childId, String childName) {
		this.parent = parent;
		this.childId = childId;
		this.childName = childName;
	}

	public Parent getParent() { return parent; }
	public void setParent(Parent parent) { this.parent = parent; }

	public String getChildId() { return childId; }
	public void setChildId(String childId) { this.childId = childId; }

	public String getChildName() { return childName; }
	public void setChildName(String childName) { this.childName = childName; }

}
