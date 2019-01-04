package jpabook.start.relationship.compositeKey.identifying.idClass.entity;

import java.io.Serializable;

import javax.persistence.Transient;

public class GrandChildId implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;
	
	private ChildId child; // GrandChild.child 매핑
	private String id; // GrandChild.id 매핑
	
	public GrandChildId() { }
	
	public GrandChildId(ChildId child, String id) {
		this.child = child;
		this.id = id;
	}

	public ChildId getChildId() { return child; }
	public void setChildId(ChildId childId) { this.child = childId; }

	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	@Override
	public int hashCode() { 
		return super.hashCode(); 
	}
	
	@Override
	public boolean equals(Object obj) { 
		return super.equals(obj); 
	}
}
