package domain;

import org.springframework.data.jpa.domain.Specification;

import domain.entity.Order;
import domain.entity.status.OrderStatus;
import spec.OrderSpec;

public class OrderSearch {

	private String memberName;
	private OrderStatus orderStatus;
	
	public OrderSearch() {}

	public OrderSearch(String memberName, OrderStatus orderStatus) {
		this.memberName = memberName;
		this.orderStatus = orderStatus;
	}

	public String getMemberName() { return memberName; }
	public void setMemberName(String memberName) { this.memberName = memberName; }

	public OrderStatus getOrderStatus() { return orderStatus; }
	public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; }
	
	public Specification<Order> toSpecification() {
		return Specification.where(OrderSpec.memberNameLike(this.memberName))
			.and(OrderSpec.orderStatusEq(this.orderStatus));
	}
}
