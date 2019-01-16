package jpabook.start.objectOrientedQuery.jpql.subQuery;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;

public class SubQueryMain extends DataInit {

	/*
	 * 서브 쿼리
	 * 
	 * JPQL도 SQL처럼 서브 쿼리를 지원한다. 여기에는 몇 가지 제약이 있는데, 서브쿼리를 WHERE, HAVING 절에서만
	 * 사용할 수 있고 SELECT, WHERE 절에서는 사용할 수 없다. 
	 * 
	 * 참고
	 * 하이버네이트의 HQL은 SELECT 절의 서브 쿼리도 허용한다. 하지만 아직까지 FROM 절의 서브쿼리는 지원하지 않는다.
	 * 일부 JPA 구현체는 FROM 절의 서브 쿼리도 지원한다.
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		Print subPrint = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("서브 쿼리");
				
				subPrint.subStartPrint("나이가 평균보다 많은 회원");
				em.createQuery("SELECT m"
					+ " FROM CH10_OOQ_MEMBER m"
					+ " WHERE m.age > (SELECT avg(m2.age) FROM CH10_OOQ_MEMBER m2)", Member.class)
				.getResultList().stream().forEach(m -> {
					System.out.println(String.format("member id : %s, member uaerName : %s, member age : %s"
						, m.getId()
						, m.getUserName()
						, m.getAge() ));
				});
				subPrint.subEndPrint();
				
				
				
				subPrint.subStartPrint("한 건이라도 주문한 고객");
				em.createQuery("SELECT m"
					+ " FROM CH10_OOQ_MEMBER m"
					+ " WHERE (SELECT COUNT(o) FROM CH10_OOQ_ORDER o WHERE m = o.member) > 0", Member.class)
				.getResultList().stream().forEach(m -> {
					System.out.println(String.format("member id : %s, member uaerName : %s, member age : %s"
						, m.getId()
						, m.getUserName()
						, m.getAge() ));
				});;
				subPrint.subEndPrint();
				
				
				
				subPrint.subStartPrint("[컬렉션 size 사용] 한 건이라도 주문한 고객");
				em.createQuery("SELECT m"
					+ " FROM CH10_OOQ_MEMBER m"
					+ " WHERE m.order.size > 0", Member.class)
				.getResultList().stream().forEach(m -> {
					System.out.println(String.format("member id : %s, member uaerName : %s, member age : %s"
						, m.getId()
						, m.getUserName()
						, m.getAge() ));
				});;
				subPrint.subEndPrint();
				
				print.mainEndPrint();
			})
			.start();
	}

}
