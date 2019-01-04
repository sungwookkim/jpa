package jpabook.start.relationship.compositeKey.nonIdentifying.idClass.entity;

import java.io.Serializable;

import javax.persistence.Transient;

/*
 * @IdClass를 사용할 때 식별자 클래스는 다음 조건을 만족해야 한다.
 * 
 * - 식별자 클래스의 속성명과 엔티티에서 사용하는 식별자의 속성명이 같아야 한다.
 * - Serializable 인터페이스를 구현해야 한다.
 * - equals, hashCode를 구현해야 한다.
 * - 기본 생성자가 있어야 한다.
 * - 식별자 클래스는 public이여야 한다.
 */
public class ParentId implements Serializable {

	@Transient
	private static final long serialVersionUID = 1L;

	private String id1;
	private String id2;	
	
	public ParentId() { }
	
	public ParentId(String id1, String id2) { 
		this.id1 = id1;
		this.id2 = id2;
	}

	public String getId1() { return this.id1; }
	public void setId1(String id1) { this.id1 = id1; }
	
	public String getId2() { return this.id2; }
	public void setId2(String id2) { this.id2 = id2; }

	@Override
	public int hashCode() { return super.hashCode(); }
	
	@Override
	public boolean equals(Object obj) { return super.equals(obj); }
}
