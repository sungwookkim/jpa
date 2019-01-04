package jpabook.start.relationship.compositeKey.nonIdentifying.idClass.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH07_COMPOSITEKEY_NONIDENTIFYING_IDCLASS_CHILD")
@SequenceGenerator(
	name = "CH07_COMPOSITEKEY_NONIDENTIFYING_IDCLASS_CHILD_SEQUENCE"
	, sequenceName = "CH07_COMPOSITEKEY_NONIDENTIFYING_IDCLASS_CHILD_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Child {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH07_COMPOSITEKEY_NONIDENTIFYING_IDCLASS_CHILD_SEQ")
	private long id;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "PARENT_ID1", referencedColumnName = "PARENT_ID1")
		, @JoinColumn(name = "PARENT_ID2", referencedColumnName = "PARENT_ID2")
	})
	private Parent parent;
	
	private String name;
	
	public Child() { }
	
	public Child(String name) {
		this.name = name;
	}

	public Child(Parent parent) {
		this.parent = parent;
	}
	
	public Child(String name, Parent parent) {
		this.name = name;
		this.parent = parent;
	}
	
	public long getId() { return id; }

	public Parent getParent() { return parent; }
	public void setParent(Parent parent) { this.parent = parent; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}
