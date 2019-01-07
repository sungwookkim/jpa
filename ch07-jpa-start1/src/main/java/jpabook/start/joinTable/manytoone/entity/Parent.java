package jpabook.start.joinTable.manytoone.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH07_JOINTABLE_MANYTOONE_PARENT")
@SequenceGenerator(
	name = "CH07_JOINTABLE_MANYTOONE_PARENT_SEQUENCE"
	, sequenceName = "CH07_JOINTABLE_MANYTOONE_PARENT_SEQ"
	, initialValue = 1
	, allocationSize = 1	
)
public class Parent {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH07_JOINTABLE_ONETOMANY_PARENT_SEQ")
	@Column(name = "PARENT_ID")
	private long id;
	
	@OneToMany(mappedBy="parent")
	private List<Child> child = new ArrayList<>();
	
	private String name;
	
	public Parent() { }
	
	public Parent(String name) {
		this.name = name;
	}
	
	public Parent(List<Child> child, String name) {
		this.child = child;
		this.name = name;
	}

	public long getId() { return id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public List<Child> getChild() { return child; }
	public void addChild(Child child) {
		if(!this.child.contains(child)) {
			this.child.add(child);
		}
		
		if(child.getParent() != this) {
			child.setParent(this);
		}
	}
}
