package jpabook.start.loading;

import java.util.Date;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.loading.entity.Member;
import jpabook.start.loading.entity.Order;
import jpabook.start.loading.entity.Product;
import jpabook.start.loading.entity.Team;

public class LoadingMain {

	/*
	 * 즉시 로딩, 지연 로딩 정리
	 * 
	 * 처음부터 연관된 엔티티를 모두 영속성 컨텍스트에 올려두는 것은 현실적이지 않다.
	 * 그렇다고 필요할 때마다 SQL을 실행해서 연관된 엔티티를 지연 로딩 하는 것도 최적화 관점에서
	 * 보면 꼭 좋은 것만은 아니다.
	 *  
	 * - 지연 로딩(LAZY)
	 * 연관된 엔티티를 프록시로 조회한다. 프록시를 실제 사용할 때 초기화하면서 데이터베이스를 조회한다.
	 * 
	 * - 즉시 로딩(EAGER)
	 * 연관된 엔티티를 즉시 조회한다. 하이버네이트는 가능하면 SQL 조인을 사용해서 한 번에 조회한다.
	 */
	public static void main(String[] args) {
		new Logic()
			.logic((em, tx) -> {
				System.out.println("=============== 즉시, 지연 로딩 저장 ===============");
				tx.begin();
				
				Team team = new Team("JAP Team");
				Member member = new Member("sinnake", "kim sung wook");
				Product product = new Product("product");
				Order order = new Order(new Date());
				
				member.setTeam(team);
				order.setMember(member);
				order.setProduct(product);
				
				em.persist(team);
				em.persist(member);
				em.persist(product);
				em.persist(order);
								
				tx.commit();
				System.out.println("====================================================");
			})
			.start();

		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				System.out.println("=============== 즉시 로딩 조회 ===============");
				em.find(Member.class, 1L);
				System.out.println("==============================================");
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				System.out.println("=============== 지연 로딩 조회 ===============");
				Member member = em.find(Member.class, 1L);
				member.getOrder().get(0);
				System.out.println("==============================================");
			})
			.start();		
	}

}
