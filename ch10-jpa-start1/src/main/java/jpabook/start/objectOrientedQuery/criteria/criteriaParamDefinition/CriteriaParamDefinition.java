package jpabook.start.objectOrientedQuery.criteria.criteriaParamDefinition;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;

public class CriteriaParamDefinition extends DataInit {

	/*
	 * 파라미터 정의
	 * 
	 * JPQL에서 :PARAM1처럼 파라미터를 정의했듯이 Criteria도 파라미터를 정의할 수 있다.
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("파라미터 정의");
				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<Tuple> cq = cb.createTupleQuery();
				Root<Member> m = cq.from(Member.class);
				
				cq.select(cb.tuple(
					m.alias("m")
				))
				// cb.parameter(타입, 파라미터 이름) 메소드를 사용해서 파라미터를 정의.
				.where(cb.equal(m.get("userName"), cb.parameter(String.class, "userNameParam")));
				
				em.createQuery(cq)
					// .setParameter("userNameParam", "sinnake1")을 사용해서 해당 파라미터에 사용할 값을 바인딩.
					.setParameter("userNameParam", "sinnake1")
					.getResultList().stream().forEach(t -> {
						Member member = t.get("m", Member.class);
	
						System.out.println(String.format("member id : %s, member userName : %s, member age : %s"
							, member.getId()
							, member.getUserName()
							, member.getAge() ));
					});
				print.mainEndPrint();

			})
			.start();

	}

}
