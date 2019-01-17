package jpabook.start.objectOrientedQuery.criteria.criteriaNativeFunctionCall;


import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;

public class CriteriaNativeFunctionCallMain extends DataInit {

	/*
	 * 네이티브 함수 호출
	 * 
	 * 하이버네이트 구현체를 사용해서 방언 클래스를 상속해서 구현하고 사용할 데이터베이스 함수를
	 * 미리 등록해야 한다.
	 * 
	 * persistence.xml 파일에 방언을 아래와 같이 등록해야 한다.
	 * <properties>
	 * 	<property name="hibernate.dialect" value="jpabook.start.objectOrientedQuery.Myh2Dialect"/>
	 * </properties>
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("네이티브 함수 호출");
				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<Tuple> cq = cb.createTupleQuery();
				Root<Member> m = cq.from(Member.class);
				
				cq.select(cb.tuple(
					cb.function("group_concat", String.class, m.get("userName")).alias("userName")
				));
				
				em.createQuery(cq).getResultList().stream().forEach(t -> {
					String sum = t.get("userName", String.class);					
					
					System.out.println(String.format("sum : %s", sum));
				});				
				print.mainEndPrint();
			})
			.start();
	}

}
