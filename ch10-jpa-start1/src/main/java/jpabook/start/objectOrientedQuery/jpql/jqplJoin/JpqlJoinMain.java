package jpabook.start.objectOrientedQuery.jpql.jqplJoin;

import java.util.List;
import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;
import jpabook.start.objectOrientedQuery.entity.Team;

public class JpqlJoinMain extends DataInit {

	/*
	 * JPQL 조인
	 * 
	 * JPQL도 조인을 지원하는데 SQL 조인과 기능은 같고 문법만 약간 다르다.
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		initSave();

		Print print = new Print();
		Print subPrint = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("내부 조인");
				/*
				 * 내부 조인
				 * 
				 * 내부 조인은 INNER JOIN을 사용한다. 참고로 INNER는 생략할 수 있다.
				 * JPQL 내부 조인 구문을 보면 SQL의 조인과 약간 다른 것을 확인할 수 있다.(INNER JOIN m.team t)
				 * JPQL 조인의 가장 큰 특징은 연관 필드를 사용한다는 것이다. 여기서 m.team이 연관 필드인데
				 * 연관 필드는 다른 엔티티와 연관관계를 가지기 위해 사용하는 필드를 말한다.
				 * ㅁ FROM Member m : 회원을 선택하고 m 이라는 별칭을 주었다.
				 * ㅁ Member m INNER JOIN m.team t : 회원이 가지고 있는 연관 필드로 팀과 조인한다.
				 * 조인한 팀에는 t라는 별칭을 주었다.
				 */
				List<Object[]> innerJoinMember = em.createQuery("SELECT m, t "
					+ " FROM CH10_OOQ_MEMBER m"
					+ " INNER JOIN m.team t"
					+ " WHERE t.name = :teamName")
				.setParameter("teamName", "우리반")
				.getResultList();
				
				innerJoinMember.stream().forEach(q -> {
					Member m = (Member)q[0];
					Team t = (Team)q[1];
					
					System.out.println(String.format("member id : %s, member userName : %s, member age : %s, member team name : %s"
						, m.getId()
						, m.getUserName()
						, m.getAge()
						, t.getName() ));
				});
				print.mainEndPrint();
				
				print.mainStartPrint("외부 조인");
				/*
				 * 외부 조인
				 * 
				 * 외부 조인은 기능상 SQL의 외부 조인과 같다. OUTER는 생략 가능해서 보통 LEFT JOIN으로 사용한다.
				 */
				List<Member> leftJoinMember = em.createQuery("SELECT m "
					+ " FROM CH10_OOQ_MEMBER m "
					+ " LEFT JOIN m.team t", Member.class)
				.getResultList();
				
				leftJoinMember.stream().forEach(l -> {
					System.out.println(String.format("member id : %s, member userName : %s, member age : %s"
						, l.getId()
						, l.getUserName()
						, l.getAge()));
				});
				
				
				
				/*
				 * 컬렉션 조인
				 * 
				 * 일대다 관계나 다대다 관계처럼 컬렉션을 사용하는 곳에 조인하는 것을 컬렉션 조인이라 한다.
				 * 
				 * ㅁ[회원 -> 팀]으로의 조인은 다대일 조인이면서 "단일 값 연관 필드(m.team)"를 사용한다.
				 * ㅁ[팀 -> 회원]은 반대로 일대다 조인이면서 "컬렉션 값 연관 필드(m.member)"를 사용한다.
				 * 
				 * 참고
				 * 컬렉션 조인 시 JOIN 대신에 IN을 사용할 수 있는데, 기능상 JOIN과 같지만 컬렉션일 때만 사용할 수 있다.
				 * 과거 EJB 시절의 유물이고 특별한 장점도 없으므로 그냥 JOIN 명령어를 사용하자.
				 */
				subPrint.subStartPrint("컬렉션 조인");
				List<Object[]>query = em.createQuery("SELECT m, t"
					+ " FROM CH10_OOQ_TEAM t LEFT JOIN t.member m")
					.getResultList();
				
				query.stream().forEach(q -> {
					Member m = (Member)q[0];
					Team t = (Team)q[1];
					
					System.out.println(String.format("member id : %s, member uaerName : %s, member age : %s, member teamName : %s"
						, m.getId()
						, m.getUserName()
						, m.getAge()
						, t.getName() ));
				});
				subPrint.subEndPrint();
				
				
				
				/*
				 * 세타 조인
				 * 
				 * WHERE 절을 사용해서 세타 조인을 할 수 있다. 참고로 "세타 조인은 내부 조인만 지원한다"
				 * 세타 조인을 사용하면 전혀 관계없는 엔티티도 조인할 수 있다.
				 * 예제를 보면 전혀 관련 없는 Member.userName과 Team.name을 조인한다.
				 */
				subPrint.subStartPrint("세타 조인");
				Long setaJoin = em.createQuery("SELECT COUNT(m)"
					+ " FROM CH10_OOQ_MEMBER m, CH10_OOQ_TEAM t"
					+ " WHERE m.userName = t.name", Long.class)
				.getSingleResult();
				
				System.out.println(String.format("member count : %s", setaJoin));
				subPrint.subEndPrint();
				
				
				
				/*
				 * JOIN ON 절(JPA 2.1)
				 * JPA 2.1부터 조인할 때 ON 절을 지원한다. ON 절을 사용하면 조인 대상을 필터링하고 조인할 수 있다.
				 * 참고로 내부 조인의 ON 절은 WHERE 절을 사용할 때와 결과가 같으므로 보통 ON 절은 외부 조인에서만 사용한다.
				 */
				subPrint.subStartPrint("JOIN ON 절(JPA 2.1)");
				query = em.createQuery("SELECT m, t"
					+ " FROM CH10_OOQ_MEMBER m"
					+ " LEFT JOIN m.team t on t.name = 'A'")
				.getResultList();
				
				query.stream().forEach(q -> {
					Member m = (Member)q[0];
					Team t = Optional.ofNullable((Team)q[1]).orElseGet(Team::new);
					
					System.out.println(String.format("member id : %s, member uaerName : %s, member age : %s, member teamName : %s"
						, m.getId()
						, m.getUserName()
						, m.getAge()
						, Optional.ofNullable(t.getName()).orElse("") ));
				});
				subPrint.subEndPrint();
				
				print.mainEndPrint();
			})
			.start();
	}

}
