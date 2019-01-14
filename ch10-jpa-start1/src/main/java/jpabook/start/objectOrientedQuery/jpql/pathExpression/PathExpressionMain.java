package jpabook.start.objectOrientedQuery.jpql.pathExpression;

import java.util.List;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.objectOrientedQuery.jpql.JpqlCommon;
import jpabook.start.objectOrientedQuery.jpql.entity.Member;
import jpabook.start.objectOrientedQuery.jpql.entity.Team;

public class PathExpressionMain extends JpqlCommon {

	/*
	 * 경로 표현식
	 * 
	 * 경로 표현식의 용어 정리
	 * ㅁ 상태 필드(state filed) : 단순히 값을 저장하기 위한 필드(필드 OR 프로퍼티)
	 * ㅁ 연관 필드(association filed) : 연관관계를 위한 필드, 임베디드 타입 포함(필드 OR 프로퍼티)
	 * 	- 단일 값 연관 필드 : @ManyToOne, @OneToOne, 대상이 엔티티
	 * 	- 컬렉션 값 연관 필드 : @OneToMany, @ManyToMany, 대상이 컬렉션
	 * 
	 * 경로 표현식과 특징
	 * JPQL에서 경로 표현식을 사용해서 경로 탐색을 하려면 다음 3가지 경로에 따라 어떤 특징이 있는지
	 * 이해해야 한다. 
	 * ㅁ 상태 필드 경로 : 경로 탐색의 끝이다. 더는 탐색할 수 없다.
	 * ㅁ 단일 값 연관 경로 : "묵시적으로 내부 조인"이 일어난다. 단일 값 연관 경로는 계속 탐색할 수 있다.
	 * ㅁ 컬렉션 값 연관 경로 : "묵시적으로 내부 조인"이 일어난다. 더는 탐색할 수 없다.
	 * 단 FROM 절에서 조인을 통해 별칭을 얻으면 별칭으로 탐색할 수 있다.
	 * 
	 * 경로 탐색을 사용한 묵시적 조인 시 주의사항
	 * 경로 탐색을 사용하면 묵시적 조인이 발생해서 SQL에서 내부 조인이 일어날 수 있다.
	 * 이때 주의사항은 다음과 같다.
	 * ㅁ 항상 내부 조인이다.
	 * ㅁ 컬렉션은 경로 탐색의 끝이다. 컬렉션에서 경로 탐색을 하려면 명시적으로 조인해서 별칭을 얻어야 한다.
	 * ㅁ 경로 탐색은 주로 SELECT, WHERE 절(다른 곳에서도 사용 됨)에서 사용하지만 묵시적 조인으로 인해
	 * SQL의 FROM 절에 영향을 준다.
	 * 
	 * 조인이 성능상 차지하는 부분은 아주 크다. 묵시적 조인은 조인이 일어나는 상황을 한눈에 파악하기 어렵다는
	 * 단점이 있다.
	 * 따라서 단순하고 성능에 이슈가 없으면 크게 문제가 안 되지만 성능이 중요하면 분석하기 쉽도록 묵시적 조인
	 * 보다는 명시적 조인을 사용하자.
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		initSave();

		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				System.out.println("=============== 경로 표현식 ===============");
				
				System.out.println("--------------- 상태 필드 경로 탐색 ---------------");
				List<Object[]> query = em.createQuery("SELECT "
					+ "m.userName"
					+ ", m.age"
					+ " FROM CH10_OOQ_MEMBER m")
				.getResultList();
				
				query.stream().forEach(q -> {
					String userName = (String)q[0];
					Integer age = (Integer)q[1];
					
					System.out.println(String.format("userName : %s, age : %s", userName, age));
				});
				System.out.println("---------------------------------------------------");
				
				/*
				 * 단일 값 연관 경로 탐색
				 * 
				 * JPQL을 보면 o.member를 통해 주문에서 회원으로 단일 값 연관 필드로 경로 탐색을 했다.
				 * "단일 값 연관 필드로 경로 탐색을 하면 SQL에서 내부 조인이 일어나는데 이것을 묵시적 조인이라 한다.
				 * 참고로 묵시전 조인은 모두 내부 조인이다."
				 * 외부 조인은 명시저그로 JOIN 키워드를 사용해야 한다.
				 */
				System.out.println("--------------- [묵시적 조인] 단일 값 연관 경로 탐색 ---------------");
				List<Member> orderMember = em.createQuery("SELECT o.member FROM CH10_OOQ_ORDER o", Member.class)
					.getResultList();
				
				orderMember.stream().forEach(m -> {
					System.out.println(String.format("user id : %s, user name : %s, user age : %s"
						, m.getId()
						, m.getUserName()
						, m.getAge() ));
				});
				System.out.println("--------------------------------------------------------------------");

				System.out.println("--------------- [묵시적 조인] 단일 값 연관 경로 탐색 ---------------");
				List<Team> memberTeam = em.createQuery("SELECT o.member.team"
						+ "	FROM CH10_OOQ_ORDER o"
						+ " WHERE o.product.name = :productName AND o.address.city = :addressCity", Team.class)
				.setParameter("productName", "computer")
				.setParameter("addressCity", "sinnake1city")
				.getResultList();
				
				memberTeam.stream().forEach(t -> {
					System.out.println(String.format("team id : %s, team name : %s"
						, t.getId()
						, t.getName() ));
				});
				System.out.println("--------------------------------------------------------------------");
				
				System.out.println("--------------- [명시적 조인] 단일 값 연관 경로 탐색 ---------------");
				orderMember = em.createQuery("SELECT "
						+ " o.member "
						+ " FROM CH10_OOQ_ORDER o"
						+ " INNER JOIN o.member m", Member.class)
					.getResultList();
				
				orderMember.stream().forEach(m -> {
					System.out.println(String.format("user id : %s, user name : %s, user age : %s"
						, m.getId()
						, m.getUserName()
						, m.getAge() ));
				});
				System.out.println("--------------------------------------------------------------------");
				
				/*
				 * 컬렉션 값 연관 경로 탐색
				 * 
				 * JPQL을 다루면서 많이 하는 실수 중 하나는 컬렉션 값에서 경로 탐색을 시도하는 것이다.
				 * 
				 * select t.member.userName from Team t
				 * 위 쿼리에서 t.member 컬렉션까지는 경로 탐색이 가능하다.
				 * 하지만 t.member.userName은 경로 탐색을 시작하는 것은 허락하지 않는다.
				 * 만약 컬렉션에서 경로 탐색을 하고 싶으면 아래 코드처럼 조인을 사용해서 새로운 별칭을 획득해야 한다.
				 * 
				 * 참고로 컬렉션은 컬렉션의 크기를 구할 수 있는 size라는 특별하 기능을 사용할 수 있다.
				 * size를 사용하면 COUNT 함수를 사용하는 SQL로 적절히 변환된다.
				 * select t.member.size from Team t
				 */
				System.out.println("--------------- 컬렉션 값 연관 경로 탐색 ---------------");
				em.createQuery("SELECT m.userName FROM CH10_OOQ_TEAM t INNER JOIN t.member m", String.class)
					.getResultList()
					.stream().forEach(userName -> {
						System.out.println(String.format("member name : %s", userName));
					});
				System.out.println("--------------------------------------------------------");

				
				System.out.println("===========================================");				
			})
			.start();

	}

}
