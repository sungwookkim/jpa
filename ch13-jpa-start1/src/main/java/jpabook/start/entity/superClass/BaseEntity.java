package jpabook.start.entity.superClass;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {

	@Column(name= "REG_DATE")
	private Date regDate = new Date();
	
	@Column(name = "MODIFY_DATE")
	private Date upDate = new Date();
	
	Date getRegDate() { return regDate; }
	void setRegDate(Date regDate) { this.regDate = regDate; }
	
	Date getUpDate() { return upDate; }
	void setUpDate(Date upDate) { this.upDate = upDate; }
}
