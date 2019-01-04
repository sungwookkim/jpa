package jpabook.start.relationship.compositeKey.nonIdentifying.idClass.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity(name = "CH07_COMPOSITEKEY_NONIDENTIFYING_IDCLASS_PARENT")
@IdClass(ParentId.class)
public class Parent {

	@Id
	@Column(name = "PARENT_ID1")
	private String id1;
	
	@Id
	@Column(name = "PARENT_ID2")
	private String id2;
	
	private String name;
	
	public Parent() {}
	
	public Parent(String id1, String id2, String name) {
		this.id1 = id1;
		this.id2 = id2;
		this.name = name;
	}

	public String getId1() { return id1; }
	public void setId1(String id1) { this.id1 = id1; }

	public String getId2() { return id2; }
	public void setId2(String id2) { this.id2 = id2; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
}
