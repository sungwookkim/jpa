package jpabook.start.objectOrientedQuery.criteria.criteriaSubQuery;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;
import jpabook.start.objectOrientedQuery.entity.Team;

public class CriteriaSubQueryMain extends DataInit {

	/*
	 * 서브 쿼리
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		Print subPrint = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("서브 쿼리");
				
				subPrint.subStartPrint("간단한 서브 쿼리");
				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<Tuple> mainQuery = cb.createTupleQuery();
				
				Subquery<Double> subQuery = mainQuery.subquery(Double.class);
				Root<Member> m2 = subQuery.from(Member.class);
				
				subQuery.select(cb.avg(m2.<Integer>get("age")));
				
				Root<Member> m = mainQuery.from(Member.class);
				mainQuery.select(cb.tuple(
					m.alias("m")
				))
				.where(cb.ge(m.<Integer>get("age"), subQuery));
				
				em.createQuery(mainQuery).getResultList().stream().forEach(mq -> {
					Member member = mq.get("m", Member.class);

					System.out.println(String.format("member id : %s, member userName : %s, member age : %s"
						, member.getId()
						, member.getUserName()
						, member.getAge() ));
				});												
				subPrint.subEndPrint();
				
				
				
				/*
				 * 상호 관련 서브 쿼리
				 * 메인 쿼리와 서브 쿼리 간에 서로 관련이 있을 때 Criteria를 어떻게 작성하는지 알아보자
				 * 서브 쿼리에서 메인 쿼리의 정보를 사용하려면 메인 쿼리에서 사용한 별칭을 얻어야 한다.
				 * 서브 쿼리는 메인 쿼리의 Root나 Join을 통해 생성된 별칭을 사용할 수 있다.
				 * 
				 * correlate 메소드를 이용해서 메인 쿼리의 별칭을 사용할 수 있따.
				 * 
				 * 남반에 소속된 회원을 조회.
				 */
				subPrint.subStartPrint("상호 관련 서브 쿼리");
				cb = em.getCriteriaBuilder();
				mainQuery = cb.createTupleQuery();
				m = mainQuery.from(Member.class);
				
				Subquery<Team> teamSubQuery = mainQuery.subquery(Team.class);				
				// 메인 쿼리의 별칭을 얻어옴.
				Root<Member> subM = teamSubQuery.correlate(m);
				
				Join<Member, Team> t = subM.join("team");
				teamSubQuery.select(t)
					.where(cb.equal(t.get("name"), "남반"));
				
				mainQuery.select(
					cb.tuple(
						m.alias("m")
						, m.get("team").alias("team")
				))
				.where(cb.exists(teamSubQuery));
				
				em.createQuery(mainQuery).getResultList().stream().forEach(mq -> {
					Member member = mq.get("m", Member.class);
					Team team = mq.get("team", Team.class);

					System.out.println(String.format("member id : %s, member userName : %s, member age : %s, team name : %s"
						, member.getId()
						, member.getUserName()
						, member.getAge()
						, team.getName() ));
				});
				subPrint.subEndPrint();
								
				print.mainEndPrint();
			})
			.start();
	}

}
