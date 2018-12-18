package jpabook.start.primaryKey.identity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "IDENTITY_BOARD")
public class IdentityBoard {

	@Id
	@Column(name = "id")
	/*
	 * IDENTITY 전략은 jpa 기본 키 값을 얻어오기 위해 데이터베이스를 추가로 조회한다. 
	 */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "data")
	private String data;

	public IdentityBoard() { }
	
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
