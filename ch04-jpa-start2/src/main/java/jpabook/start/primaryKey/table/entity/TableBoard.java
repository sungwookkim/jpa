package jpabook.start.primaryKey.table.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity(name = "TABLE_BOARD")
@TableGenerator(
	name = "TABLE_BOARD_SEQ_GENERATOR"
	, table = "MY_SEQUENCES"
	, pkColumnValue = "TABLE_BOARD_SEQ"
	, allocationSize = 1
)
public class TableBoard {

	@Id
	@GeneratedValue(
		strategy = GenerationType.TABLE
		, generator = "TABLE_BOARD_SEQ_GENERATOR"
	)
	private long id;
	
	private String data;
	
	public TableBoard() { }
	
	public long getId() { return this.id; }
	public void setId(long id) { this.id = id; }
	
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
