package jpabook.start.relationship.compositeKey.identifying.embeddedId.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class ChildId implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	private long parentId; // @MapsId("parentId")로 매핑.
	
	@Column(name = "CHILD_ID")
	private String id;
	
	public ChildId() { }
	
	public ChildId(long parentId, String id) {
		this.parentId = parentId;
		this.id = id;
	}

	public long getParentId() { return parentId; }
	public void setParentId(long parentId) { this.parentId = parentId; }

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
