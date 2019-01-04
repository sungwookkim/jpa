package jpabook.start.relationship.compositeKey.nonIdentifying.embeddedId.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
/*
 * @EmbeddedId
 * 
 * @IdClass가 데이터베이스에 맞춘 방법이라면 @EmbeddedId는 좀 더 객체지향적인 방법이다.
 * @IdClass와는 다르게 @EmbeddedId를 적용한 식별자 클래스는 식별자 클래스에 기본 키를 직접 매핑한다.
 * 
 * @EmbeddedId를 적용한 식별자 클래스는 다음 조건을 만족해야 한다.
 * - @EmbeddedId 어노테이션을 붙여주어야 한다.
 * - Serializable 인터페이스를 구현해야 한다.
 * - equals, hashCode를 구현해야 한다.
 * - 기본 생성자가 있어야 한다.
 * - 식별자 클래스는 public이어야 한다.
 */

@Embeddable
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

	public String getId1() { return id1; }
	public void setId1(String id1) { this.id1 = id1; }

	public String getId2() { return id2; }
	public void setId2(String id2) { this.id2 = id2; }

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
