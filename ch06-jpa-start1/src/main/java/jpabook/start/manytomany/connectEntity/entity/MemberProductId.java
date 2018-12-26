package jpabook.start.manytomany.connectEntity.entity;

import java.io.Serializable;

@SuppressWarnings("unused")
public class MemberProductId implements Serializable {

	private static final long serialVersionUID = -5813089113789211502L;

	// MemberProduct.member와 연결
	private long member;
	
	// MemberProduct.product와 연결
	private long product;
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
