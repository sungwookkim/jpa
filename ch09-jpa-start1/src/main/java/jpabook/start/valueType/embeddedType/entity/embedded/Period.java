package jpabook.start.valueType.embeddedType.entity.embedded;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class Period {
	
	@Temporal(TemporalType.DATE) Date startDate;
	@Temporal(TemporalType.DATE) Date endDate;
	
	public Period() { }
	
	public Period(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Date getStartDate() { return startDate; }

	public Date getEndDate() { return endDate; }
	
	/*
	 * 해당 값 타입만 사용하는 의미 있는 메소드도 만들 수 있다.
	 */
	public boolean isWork(Date date) {
		if(date.getTime() >= this.startDate.getTime() && this.endDate.getTime() <= date.getTime()) {
			return true;
		}
		
		return false;
	}
}
