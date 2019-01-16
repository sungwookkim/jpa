package jpabook.start.objectOrientedQuery.criteria.criteriaIn;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;

public class CriteriaInMain extends DataInit {

	/*
	 * IN 식
	 */
	public static void main(String[] args) {
		initSave();

		Print print = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("IN 식");

				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<Tuple> cq = cb.createTupleQuery();
				Root<Member> m = cq.from(Member.class);
				
				cq.select(cb.tuple(
					m.alias("m")
				))
				.where(cb.in(m.get("userName"))
					.value("sinnake1")
					.value("sinnake2")
				);
				
				em.createQuery(cq).getResultList().stream().forEach(t -> {
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
