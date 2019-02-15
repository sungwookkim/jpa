package spec;

import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import domain.entity.Member;
import domain.entity.Order;
import domain.entity.status.OrderStatus;

public class OrderSpec {

	public static Specification<Order> memberNameLike(final String memberName) {
		return new Specification<Order>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				return Optional.ofNullable(memberName)
					.map(mn -> {
						Join<Order, Member> m = root.join("member", JoinType.INNER);
						return criteriaBuilder.like(m.<String>get("name"), "%" + mn + "%");
					})
					.orElseGet(() -> null);				
			}
		};
	}
	
	public static Specification<Order> orderStatusEq(final OrderStatus orderStatus) {
		return new Specification<Order>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				return Optional.ofNullable(orderStatus)
					.map(os -> criteriaBuilder.equal(root.get("status"), os))
					.orElseGet(() -> null);
			}
			
		};
	}
	

}
