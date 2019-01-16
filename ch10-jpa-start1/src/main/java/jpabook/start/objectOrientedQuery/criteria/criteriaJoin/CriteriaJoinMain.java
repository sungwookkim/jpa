package jpabook.start.objectOrientedQuery.criteria.criteriaJoin;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;
import jpabook.start.objectOrientedQuery.entity.Team;

public class CriteriaJoinMain extends DataInit {

	/*
	 * 조인
	 * 
	 * 쿼리 루트(m)에서 바로 m.join("team") 메소드를 사용해서 회원과 팀을 조인했다.
	 * 그리고 조인한 team에 t라는 별칭을 주었다.
	 * 여기서는 JoinType.INNER를 설정해서 내부 조인을 사용했다.
	 * 참고로 조인 타입을 생략하ㅏ면 내부 조인을 사용한다.
	 * 외부 조인은 JoinType.LEFT로 설정하면 된다.
	 * 
	 * m.join("team") // 내부 조인
	 * m.join("team", JoinType.INNER) // 내부 조인
	 * m.join("team", JoinType.LEFT) // 외부 조인
	 */
	public static void main(String[] args) {
		initSave();

		Print print = new Print();
		Print subPrint = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("조인");
				
				subPrint.subStartPrint("INNER JOIN");
				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<Tuple> cq = cb.createTupleQuery();
				Root<Member> m = cq.from(Member.class);
				
				Join<Member, Team> t = m.join("team", JoinType.INNER);
				
				cq.multiselect(m.alias("m"), t.alias("t"))
					.where(cb.equal(t.get("name"), "우리반"));

				em.createQuery(cq).getResultList().stream().forEach(join -> {
					Member member = join.get("m", Member.class);
					Team team = join.get("t", Team.class);
					
					System.out.println(String.format("member name : %s, member userName : %s, member age : %s"
						+ ", team id : %s, team name : %s"
						, member.getId()
						, member.getUserName()
						, member.getAge()
						, team.getId()
						, team.getName() ));
				});
				subPrint.subEndPrint();

				
				
				/*
				 * FETCH JOIN
				 * 페치 조인은 fetch(조인대상, JoinType)을 사용한다.
				 * 페치 조인 시 주의사항은 JPQL과 같다.
				 */
				subPrint.subStartPrint("FETCH JOIN");
				cq = cb.createTupleQuery();
				m = cq.from(Member.class);
				m.fetch("team", JoinType.LEFT);
				
				cq.multiselect(m.alias("m"));					

				em.createQuery(cq).getResultList().stream().forEach(fetch -> {
					Member member = fetch.get("m", Member.class);
					
					System.out.println(String.format("member name : %s, member userName : %s, member age : %s"						
						, member.getId()
						, member.getUserName()
						, member.getAge() ));
				});				
				subPrint.subEndPrint();

				print.mainEndPrint();
			})
			.start();

	}

}
