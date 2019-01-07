package jpabook.start.joinTable.onetoone.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH07_JOINTABLE_ONETOONE_CHILD")
@SequenceGenerator(
	name = "CH07_JOINTABLE_ONETOONE_CHILD_SEQUENCE"
	, sequenceName = "CH07_JOINTABLE_ONETOONE_CHILD_SEQ"
	, initialValue = 1
	, allocationSize = 1	
)
public class Child {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH07_JOINTABLE_ONETOONE_CHILD_SEQ")
	@Column(name = "CHILD_ID")
	private long id;

	@OneToOne(mappedBy = "child")
	private Parent parent;
	
	private String name;
	
	public Child() { }
	
	public Child(String name) {
		this.name = name;
	}
	
	public Child(Parent parent, String name) {
		this.parent = parent;
		this.name = name;
	}
	
	public long getId() { return id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Parent getParent() { return parent; }
	public void setParent(Parent parent) { 
		this.parent = parent;
		
		if(parent.getChild() != this) {
			parent.setChild(this);
		}
	}	
}
