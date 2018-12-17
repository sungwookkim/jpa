package jpabook.start.primaryKey.direct.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "DIRECT_BOARD")
public class DirectBoard {

	@Id	
	@Column(name = "id")
	private long id;
		
	public DirectBoard() { }
	
	public long getId() { return this.id; }
	public void setId(long id) { this.id = id; }
	
	@Override
	public String toString() {	
		return "{" 
			+ "'id' : " + this.id
			+ "}";
	}
}
