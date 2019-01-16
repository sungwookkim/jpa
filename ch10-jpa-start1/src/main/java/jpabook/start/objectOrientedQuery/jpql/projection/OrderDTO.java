package jpabook.start.objectOrientedQuery.jpql.projection;

import jpabook.start.objectOrientedQuery.entity.Member;
import jpabook.start.objectOrientedQuery.entity.Product;

public class OrderDTO {
	private Member member;
	private Product product;
	private int orderAmount;
	
	public OrderDTO(Member member, Product product, int orderAmount) {
		this.member = member;
		this.product = product;
		this.orderAmount = orderAmount;
	}

	public Member getMember() { return member; }

	public Product getProduct() { return product; }

	public int getOrderAmount() { return orderAmount; }	
}