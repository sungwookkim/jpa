package jpabook.start.objectOrientedQuery.jpql.pagingApi;

import java.util.List;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.objectOrientedQuery.jpql.JpqlCommon;
import jpabook.start.objectOrientedQuery.jpql.entity.Order;

public class PagingApiMain extends JpqlCommon {
	/*
	 * 페이징 API
	 * 
	 * 페이징 처리용 SQL을 작성하는 일은 지루하고 반복적이다. 
	 * 더 큰 문제는 데이터베이스마다 페이징을 처리하는 SQL문법이 다르다는 점이다.
	 * 
	 * 데이터베이스마다 다른 페이징 처리를 같은 API로 처리할 수 있는 것은 데이터베이스 방언(Dialect) 덕분이다.
	 * 
	 * 페이징 SQL을 더 최적화하고 싶다면 JPA가 제공하는 페이징 API가 아닌 네이티브 SQL을 직접 사용해야 한다.
	 * 
	 * JPA는 페이징을 다음 두 API로 추상화 했다.
	 * - setFirstResult(int startPosition) : 조회 시작 위치(0부터 시작한다.)
	 * - setMaxResults(int maxResult) : 조회할 데이터 수
	 */
	public static void main(String[] args) {
		initSave();

		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				System.out.println("=============== 페이징 API ===============");
				List<Order> orders = em.createQuery("SELECT o FROM CH10_OOQ_ORDER o", Order.class)
					// 시작 위치는 10이므로 11번째부터 시작한다.
					.setFirstResult(10)
					// 조최할 총 데이터 건수는 5개이다.
					.setMaxResults(5)
					.getResultList();
				
				orders.stream().forEach(o -> {
					System.out.println("order id : " + o.getId());
					System.out.println("order orderAmount : " + o.getOrderAmount());
				});
				System.out.println("==========================================");
			})
			.start();
	}

}
