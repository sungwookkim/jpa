package jpabook.start.orphan.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH08_ORPHAN_CHILD")
@SequenceGenerator(
	name = "CH08_ORPHAN_CHILD_SEQUENCE"
	, sequenceName = "CH08_ORPHAN_CHILD_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class Child {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH08_ORPHAN_CHILD_SEQ")
	@Column(name = "CH08_ORPHAN_CHILD_SEQ")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private Parent parent;
	
	private String name;
	
	public Child() { }
	
	public Child(String name) {
		this.name = name;
	}

	public long getId() { return id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public Parent getParent() { return parent; }
	public void setParent(Parent parent) {
		Optional.ofNullable(this.parent)
			.ifPresent(p -> p.getChild().remove(this));

		this.parent = parent;
		
		if(!parent.getChild().contains(this)) {
			parent.getChild().add(this);
		}
	}	
}
