package jpabook.start.objectOrientedQuery.criteria.criteriaBasics;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;

public class CriteriaBasicsMain extends DataInit {

	/*
	 * Criteria 기초
	 * 
	 * Criteria API는 javax.persistence.criteria 패키지에 있다.
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		Print subPrint = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {		
				print.mainStartPrint("Criteria 기초");

				subPrint.subStartPrint("Criteria 쿼리 시작");
				/*
				 * Criteria 쿼리를 생성하려면 먼저 Criteria 빌더(CriteriaBuilder)를 얻어야 한다.
				 * Criteria 빌더는 EntityManager나 EntityManagerFactory에서 얻을 수 있다.
				 */
				CriteriaBuilder cb = em.getCriteriaBuilder();
				
				/*
				 * Criteria 쿼리 빌더에서 Criteria 쿼리(CriteriaQuery)를 생성한다.
				 * 이때 반환 타입을 지정할 수 있다.
				 */
				CriteriaQuery<Member> cq = cb.createQuery(Member.class);
				
				/*
				 * FROM 절을 생성한다.
				 * 반환된 값 m은 Criteria에서 사용하는 특별한 별칭이다.
				 * m을 조회의 시작점이라는 의미로 쿼리 루트(Root)라 한다.
				 */
				Root<Member> m = cq.from(Member.class);
				/*
				 * SELECT 절을 생성한다.
				 */
				cq.select(m);
				
				TypedQuery<Member> query = em.createQuery(cq);
				List<Member> members = query.getResultList();
				
				members.stream().forEach(ms -> {
					System.out.println(String.format("member id : %s, member username : %s, member age : %s"
						, ms.getId()
						, ms.getUserName()
						, ms.getAge() ));
				});
				subPrint.subEndPrint();

				
				
				subPrint.subStartPrint("검색 조건 추가");
				/*
				 * 검색 조건을 정의한 부분을 보면 m.get("userName")으로 되어 있는데 m은 회원 엔티티의 별칭이다.
				 * 이것은 JPQL에서 m.userName과 같은 표현이다. 그리고 cb.equal(A, B)는 이름 그대로 A = B라는 뜻이다.
				 * 따라서 cb.equal(m.get("userName"), "sinnake1")는 JPQL에서 m.userName = 'sinnake1'과 같은 표현이다.
				 */
				Predicate userNameEqual = cb.equal(m.get("userName"), "sinnake1");
				/*
				 * 정렬 조건을 정의하는 코드인 cb.desc(m.get("age"))는 JPQL의 m.age desc와 같은 표현이다.
				 */
				Order ageDesc = cb.desc(m.get("age"));
				
				/*
				 * 만들어둔 조건을 where, orderBy에 넣어서 원하는 쿼리를 생성한다.
				 */
				cq.select(m)
					.where(userNameEqual)
					.orderBy(ageDesc);
				
				em.createQuery(cq)
					.getResultList().stream().forEach(ms -> {
						System.out.println(String.format("member id : %s, member username : %s, member age : %s"
								, ms.getId()
								, ms.getUserName()
								, ms.getAge() ));					
					});
				subPrint.subEndPrint();
				
				
				
				/*
				 * 쿼리 루트(Query Root)와 별칭
				 * 
				 * ㅁ Root<Member> m = cq.from(Member.class); 여기서 m이 쿼리 루트다.
				 * ㅁ 쿼리 루트는 조회의 시작점이다.
				 * ㅁ Criteria에서 사용되는 특별한 별칭이다. JPQL의 별칭이라 생각하면 된다.
				 * ㅁ 별칭은 엔티티에만 부여할 수 있다.
				 * 
				 * Criteria 코드로 JPQL을 완성하는 도구다. 따라서 경로 표현식도 있다.
				 * ㅁ m.get("userName")는 JPQL의 m.userName과 같다.
				 * ㅁ m.get("team").get("name")는 JPQL의 m.team.name과 같다.
				 */
				
				
				
				subPrint.subStartPrint("숫자 타입 검색");
				/*
				 * 10살을 초과하는 회원 조건
				 * 
				 * m.get("age")에서 age의 타입 정보를 알지 못하기 때문에
				 * m.<Integer>get("age")와 같이 제네릭으로 타입 정보를 알려줘야 한다.
				 * 
				 * 참고로 greateThan() 대신에 gt()를 사용해도 된다.
				 */
				Predicate ageGt = cb.greaterThan(m.<Integer>get("age"), 10);
				
				cq.select(m)
					.where(ageGt)
					.orderBy(cb.desc(m.get("age")));
				
				em.createQuery(cq)
					.getResultList().stream().forEach(ms -> {
						System.out.println(String.format("member id : %s, member username : %s, member age : %s"
								, ms.getId()
								, ms.getUserName()
								, ms.getAge() ));					
					});
				subPrint.subEndPrint();
				
				print.mainEndPrint();
			})
			.start();
	}
}
