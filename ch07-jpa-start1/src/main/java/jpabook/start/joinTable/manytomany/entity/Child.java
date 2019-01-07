package jpabook.start.joinTable.manytomany.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH07_JOINTABLE_MANYTOMANY_CHILD")
@SequenceGenerator(
	name = "CH07_JOINTABLE_MANYTOMANY_CHILD_SEQUENCE"
	, sequenceName = "CH07_JOINTABLE_MANYTOMANY_CHILD_SEQ"
	, initialValue = 1
	, allocationSize = 1	
)
public class Child {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH07_JOINTABLE_MANYTOMANY_CHILD_SEQ")
	@Column(name = "CHILD_ID")
	private long id;
	
	private String name;
	
	public Child() { }
	
	public Child(String name) {
		this.name = name;
	}

	public long getId() { return id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}
