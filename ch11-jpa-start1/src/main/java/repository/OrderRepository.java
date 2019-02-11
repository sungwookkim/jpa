package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import domain.OrderSearch;
import domain.entity.Member;
import domain.entity.Order;

@Repository
public class OrderRepository {

	@PersistenceContext
	EntityManager em;
	
	public void save(Order order) {
		em.persist(order);
	}
	
	public Order findOne(Long id) {
		return em.find(Order.class, id);
	}
	
	public List<Order> findAll(OrderSearch orderSearch) {
	
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<Order> orderRoot = cq.from(Order.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		Optional.ofNullable(orderSearch.getOrderStatus())
			.map(os -> cb.equal(orderRoot.get("status"), os))
			.ifPresent(c -> predicates.add(c));

		Optional.ofNullable(orderSearch.getMemberName())
			.filter(mn -> StringUtils.hasText(mn))
			.map(mn -> {
				Join<Order, Member> m = orderRoot.join("member", JoinType.INNER);
				
				return cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");				
			})
			.ifPresent(c -> predicates.add(c));

		cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()]) ));

		TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
		
		return query.getResultList();
	}
	
}
