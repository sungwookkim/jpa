package jpabook.start.objectOrientedQuery.queryDSL.queryDSLJoin;

import java.util.List;

import javax.persistence.EntityManager;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Order;
import jpabook.start.objectOrientedQuery.entity.QMember;
import jpabook.start.objectOrientedQuery.entity.QOrder;

public class QueryDSLJoinMain extends DataInit {

	/*
	 * 조인
	 * 
	 * innerJoin(join), leftJoin, rightJoin, fullJoin을 사용할 수 있고 추가로 JPQL의 on과
	 * 성능 최적화를 위한 fetch 조인도 사용할 수 있다.
	 * 
	 * 조인의 기본 문법은 첫 번째 파라미터에 조인 대상을 지정하고, 두 번째 파라미터에 별칭(alias)으로
	 * 사용할 쿼리 타입을 지정하면 된다.
	 * 
	 * 	join(조인 대상, 별칭으로 사용할 쿼리 타입)
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("조인");
				JPAQuery<EntityManager> query = new JPAQuery<>(em);
				QMember member = QMember.member;
				QOrder order = QOrder.order;
				
				List<Tuple> orders = query.select(order, member.userName, order.product.name)
					.from(order)
					.join(order.member, member)
					.fetch();
				
				orders.stream().forEach(t -> {
					Order o = t.get(order);
					String un = t.get(member.userName);
					String pn = t.get(order.product.name);
										
					System.out.println(String.format("member name : %s, order id : %s, order amount : %s, product name : %s"
						, un
						, o.getId()
						, o.getOrderAmount()
						, pn ));
				});
				print.mainEndPrint();
			})
			.start();

	}

}
