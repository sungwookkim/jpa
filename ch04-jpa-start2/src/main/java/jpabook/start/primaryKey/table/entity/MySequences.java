package jpabook.start.primaryKey.table.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "MY_SEQUENCES")
public class MySequences {

	@Id
	@Column(name = "sequence_name", nullable = false)
	String sequenceName;
	
	@Column(name = "next_val")
	long nextVal;
	
	public MySequences() { }
	
	public String getSequenceName() { return this.sequenceName; }
	
	public long getNextVal() { return this.nextVal; }
	public void setNextVal(long nextVal) { this.nextVal = nextVal; }
}
