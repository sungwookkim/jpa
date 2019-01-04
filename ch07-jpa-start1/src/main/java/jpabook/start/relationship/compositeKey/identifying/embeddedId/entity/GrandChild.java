package jpabook.start.relationship.compositeKey.identifying.embeddedId.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity(name = "CH07_COMPOSITEKEY_IDENTIFYING_EMBEDDEBID_GRANDCHILD")
public class GrandChild {

	@EmbeddedId
	private GrandChildId grandChildId;
	
	@MapsId("childId")
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "PARENT_ID")
		, @JoinColumn(name = "CHILD_ID")
	})
	private Child childId;
	
	private String name;
	
	public GrandChild() { }
	
	public GrandChild(Child child, GrandChildId grandChildId, String name) {
		this.childId = child;
		this.grandChildId = grandChildId;
		this.name = name;
	}

	public GrandChildId getGrandChildId() { return grandChildId; }
	public void setGrandChildId(GrandChildId grandChildId) { this.grandChildId = grandChildId; }

	public Child getChildId() { return childId; }
	public void setChildId(Child childId) { this.childId = childId; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}
