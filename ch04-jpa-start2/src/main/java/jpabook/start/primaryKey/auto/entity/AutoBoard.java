package jpabook.start.primaryKey.auto.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "AUTO_BOARD")
public class AutoBoard {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String data;
	
	public AutoBoard() { }
	
	public long getId() { return this.id; }
	
	public String getData() { return this.data; }
	public void setData(String data) { this.data = data; }
	
	@Override
	public String toString() {	
		return "{" 
			+ "'id' : " + this.id
			+ ", 'data' : " + this.data
			+ "}";
	}
	
}
