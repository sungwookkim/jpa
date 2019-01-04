package jpabook.start.relationship.compositeKey.identifying.idClass.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity(name = "CH07_COMPOSITEKEY_IDENTIFYING_IDCLASS_GRANDCHILD")
@IdClass(GrandChildId.class)
public class GrandChild {

	@Id
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "PARENT_ID")
		, @JoinColumn(name = "CHILD_ID")
	})
	private Child child;
	
	@Id
	@Column(name = "GRANDCHILD_ID")
	private String id;
	
	private String grandChildName;
	
	public GrandChild() { }
	
	public GrandChild(Child child, String id, String grandChildName) {
		this.child = child;
		this.id = id;
		this.grandChildName = grandChildName;
	}

	public Child getChild() { return child; }
	public void setChild(Child child) { this.child = child; }

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public String getGrandChildName() { return grandChildName; }
	public void setGrandChildName(String grandChildName) { this.grandChildName = grandChildName; }
}
