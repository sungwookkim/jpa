package jpabook.start.relationship.compositeKey.nonIdentifying.embeddedId.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name = "CH07_COMPOSITEKEY_NONIDENTIFYING_EMBEDDEBID_PARENT")
public class Parent {

	@EmbeddedId
	private ParentId id;
	
	private String name;
	
	public Parent() { }
	
	public Parent(ParentId id, String name) {
		this.id = id;
		this.name = name;
	}

	public ParentId getId() { return id; }
	public void setId(ParentId id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getId1() { return this.id.getId1(); }
	public void setId1(String id1) { this.id.setId1(id1); }
	
	public String getId2() { return this.id.getId2(); }
	public void setId2(String id2) { this.id.setId2(id2); }
}
