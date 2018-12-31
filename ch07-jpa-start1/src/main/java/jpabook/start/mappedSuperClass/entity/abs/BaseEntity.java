package jpabook.start.mappedSuperClass.entity.abs;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@GeneratedValue
	private long id;
	private String name;
	
	public long getId() { return id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}
