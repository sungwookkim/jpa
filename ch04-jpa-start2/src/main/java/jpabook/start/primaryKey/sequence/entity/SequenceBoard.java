package jpabook.start.primaryKey.sequence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "SEQUENCE_BOARD")
@SequenceGenerator(
	name = "SEQUENCE_BOARD_SEQ_GENRATOR"
	, sequenceName = "SEQUENCE_BOARD_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public class SequenceBoard {

	@Id
	@Column(name = "id")
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE
		, generator = "SEQUENCE_BOARD_SEQ_GENRATOR"
	)
	private long id;
	
	private String data;
	
	public SequenceBoard() { }
	
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
