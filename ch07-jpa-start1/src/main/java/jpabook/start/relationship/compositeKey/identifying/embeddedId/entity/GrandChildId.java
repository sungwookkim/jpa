package jpabook.start.relationship.compositeKey.identifying.embeddedId.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class GrandChildId implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	
	private ChildId childId; // @MapsId("childId")로 매핑.
	
	@Column(name = "GRANDCHILD_ID")
	private String id;

	public GrandChildId() { }

	public GrandChildId(ChildId childId, String id) {
		this.childId = childId;
		this.id = id;
	}	
	
	public ChildId getChildId() { return childId; }
	public void setChildId(ChildId childId) { this.childId = childId; }

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
