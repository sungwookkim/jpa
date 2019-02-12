package repository.queryDSL.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import com.querydsl.jpa.JPQLQuery;

import domain.OrderSearch;
import domain.entity.Order;
import domain.entity.QMember;
import domain.entity.QOrder;
import repository.queryDSL.inter.CustomOrderRepository;

public class OrderRepositoryImpl extends QuerydslRepositorySupport implements CustomOrderRepository {
	
	public OrderRepositoryImpl() {
		super(Order.class);
	}

	@Override
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		super.setEntityManager(entityManager);
	}
	
	@Override
	public List<Order> search(OrderSearch orderSearch) {

		QOrder qOrder = QOrder.order;
		QMember qMember = QMember.member;
		
		JPQLQuery<Order> query = from(qOrder);

		Optional.ofNullable(orderSearch.getMemberName())
			.filter(StringUtils::hasText)
			.ifPresent(mn -> {
				query.leftJoin(qOrder.member, qMember)
					.where(qMember.name.contains(mn));
			});
				
		Optional.ofNullable(orderSearch.getOrderStatus())
			.ifPresent(os -> {
				query.where(qOrder.status.eq(os));
			});

		return query.fetch();
	}

}
