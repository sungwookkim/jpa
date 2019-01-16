package jpabook.start.objectOrientedQuery.criteria.criteriaOrder;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;

public class CriteriaOrderMain extends DataInit {

	/*
	 * 정렬
	 * 
	 * 정렬 조건도 Criteria 빌더를 통해서 생성한다.
	 * cb.desc(...) 또는 cb.asc(...로 생성할 수 있다.
	 */
	public static void main(String[] args) {
		initSave();

		Print print = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("정렬");
				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<Tuple> cq = cb.createTupleQuery();
				Root<Member> m = cq.from(Member.class);

				cq.select(cb.tuple(m.alias("m")))
					.where(cb.gt(m.<Integer>get("age"), 30))
					.orderBy(cb.desc(m.get("age")));

				em.createQuery(cq).getResultList().stream().forEach(t -> {
					Member member = t.get("m", Member.class);
					
					System.out.println(String.format("member name : %s, member userName : %s, member age : %s"
						, member.getId()
						, member.getUserName()
						, member.getAge() ));
				});				
				print.mainEndPrint();
			})
			.start();

	}

}
