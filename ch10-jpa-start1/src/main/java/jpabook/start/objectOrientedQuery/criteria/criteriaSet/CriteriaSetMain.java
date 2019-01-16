package jpabook.start.objectOrientedQuery.criteria.criteriaSet;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;

public class CriteriaSetMain extends DataInit {

	/*
	 * 집합
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		Print subPrint = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("집합");
				
				/*
				 * 팀 이름별로 나이가 가장 많은 사람과 가장 적은 사람을 조회.
				 */
				subPrint.subStartPrint("GROUP BY");
				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<Tuple> cq = cb.createTupleQuery();
				Root<Member> m = cq.from(Member.class);
				
				Expression<Integer> maxAge = cb.max(m.<Integer>get("age"));
				Expression<Integer> minAge = cb.min(m.<Integer>get("age"));
				
				cq.multiselect(
					m.get("team").get("name").alias("name")
					, maxAge.alias("maxAge")
					, minAge.alias("minAge")
				);
				
				// 아래 코드는 group by m.team.name과 같다.
				cq.groupBy(m.get("team").get("name"));
				
				em.createQuery(cq).getResultList().stream().forEach(t -> {
					System.out.println(String.format("team name : %s, maxAge : %s, minAge : %s"
						, t.get("name", String.class)
						, t.get("maxAge", Integer.class)
						, t.get("minAge", Integer.class) ));
				});
				subPrint.subEndPrint();
				
				
				
				/*
				 * 위 조건에서 팀에 가장 나이 어린 사람이 10살을ㄹ 초과하는 팀을 조회.
				 */
				subPrint.subStartPrint("HAVING");
				cq = cb.createTupleQuery();
				m = cq.from(Member.class);
				
				cq.multiselect(
					m.get("team").get("name").alias("name")
					, maxAge.alias("maxAge")
					, minAge.alias("minAge")
				)
				.groupBy(m.get("team").get("name"))
				// 아래 메소드는 having min(m.age) > 10과 같다.
				.having(cb.gt(minAge, 10));
				
				em.createQuery(cq).getResultList().stream().forEach(t -> {
					System.out.println(String.format("team name : %s, maxAge : %s, minAge : %s"
						, t.get("name", String.class)
						, t.get("maxAge", Integer.class)
						, t.get("minAge", Integer.class) ));
				});
				subPrint.subEndPrint();
				
				print.mainEndPrint();
			})
			.start();
	}

}
