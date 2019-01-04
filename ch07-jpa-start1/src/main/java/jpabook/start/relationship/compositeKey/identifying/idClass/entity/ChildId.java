package jpabook.start.relationship.compositeKey.identifying.idClass.entity;

import java.io.Serializable;

import javax.persistence.Transient;

public class ChildId implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	
	private long parent; // Child.parent 매핑
	private String childId; // Child.childId 매핑
	
	public ChildId() { }

	public ChildId(long parent, String childId) {
		this.parent = parent;
		this.childId = childId;
	}	

	public long getParent() { return parent; }
	public void setParent(long parent) { this.parent = parent; }

	public String getChildId() { return childId; }
	public void setChildId(String childId) { this.childId = childId; }

	@Override
	public int hashCode() {	
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {	
		return super.equals(obj);
	}
}
