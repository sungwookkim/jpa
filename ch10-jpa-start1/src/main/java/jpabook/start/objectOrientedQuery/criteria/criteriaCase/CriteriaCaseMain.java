package jpabook.start.objectOrientedQuery.criteria.criteriaCase;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;

public class CriteriaCaseMain extends DataInit {

	/*
	 * CASE 식
	 * 
	 * CASE 식에는 selectCase 메소드와 when, otherwise 메소드를 사용한다.
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("CASE 식");
				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<Tuple> cq = cb.createTupleQuery();
				Root<Member> m = cq.from(Member.class);
				
				cq.multiselect(
					m.alias("m")
					, cb.selectCase()
						.when(cb.ge(m.<Integer>get("age"), 37), 600)
						.when(cb.le(m.<Integer>get("age"), 37), 500)
						.otherwise(1000).alias("age")
				);
				
				em.createQuery(cq).getResultList().stream().forEach(t -> {
					Member member = t.get("m", Member.class);
					Integer age = t.get("age", Integer.class);

					System.out.println(String.format("member id : %s, member userName : %s, member age : %s, age : %s"
						, member.getId()
						, member.getUserName()
						, member.getAge()
						, age ));
				});
				print.mainEndPrint();
			})
			.start();
		

	}

}
