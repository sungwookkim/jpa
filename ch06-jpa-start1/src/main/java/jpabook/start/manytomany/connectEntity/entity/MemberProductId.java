package jpabook.start.manytomany.connectEntity.entity;

import java.io.Serializable;

@SuppressWarnings("unused")
public class MemberProductId implements Serializable {

	private static final long serialVersionUID = -5813089113789211502L;

	// MemberProduct.member와 연결
	private long member;
	
	// MemberProduct.product와 연결
	private long product;
	
	public MemberProductId() { }
	
	public MemberProductId(long member, long product) { 
		this.member = member;
		this.product = product;
	}
	
	public long getMember() { return this.member; }
	public void setMember(long member) { this.member = member; }
	
	public long getProduct() { return this.product; }
	public void setProduct(long product) { this.product = product; }

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
