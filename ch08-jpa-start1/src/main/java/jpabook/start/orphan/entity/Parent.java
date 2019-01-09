package jpabook.start.orphan.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH08_ORPHAN_PARENT")
@SequenceGenerator(
	name = "CH08_ORPHANE_PARENT_SEQUENCE"
	, sequenceName = "CH08_ORPHAN_PARENT_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Parent {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH08_ORPHAN_PARENT_SEQ")
	@Column(name = "PARENT_ID")
	private long id;
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Child> child = new ArrayList<>();
	
	private String name;
		
	public Parent() { }
	
	public Parent(String name) {
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
