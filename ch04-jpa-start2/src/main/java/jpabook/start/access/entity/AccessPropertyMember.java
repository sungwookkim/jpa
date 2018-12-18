package jpabook.start.access.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ACCESS_PROPERTY_MEMBER")
@Access(AccessType.PROPERTY)
public class AccessPropertyMember {
	
	private String id;
	
	private String data1;
	private String data2;
	
	@Id
	public String getId() { return this.id; }
	public void setId(String id) { this.id = id; }
	
	@Column
	public String getData1() { return this.data1; }
	public void setData1(String data1) { this.data1 = data1; }
	
	public String getData2() { return this.data2; }
	public void setData2(String data2) { this.data2 = data2; }
	
	@Override
	public String toString() {	
		return "{"
			+ "'id' : '" + this.id + "'"
			+ ", 'data1' : '" + this.data1 + "'"
			+ ", 'data2' : '" + this.data2 + "'"
			+ "}";
	}
	
}
