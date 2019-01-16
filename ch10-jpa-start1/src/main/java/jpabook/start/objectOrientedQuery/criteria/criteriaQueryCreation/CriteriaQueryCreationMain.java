package jpabook.start.objectOrientedQuery.criteria.criteriaQueryCreation;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;

public class CriteriaQueryCreationMain extends DataInit {

	/*
	 * Criteria 쿼리 생성
	 * 
	 * CriteriaBuilder.createQuery() 메소드로 Criteria 쿼리(CriteriaQuery)를 생성하면 된다.
	 */
	public static void main(String[] args) {
		initSave();

		Print print = new Print();
		Print subPrint = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("조회");
				
				subPrint.subStartPrint("조회 대상을 한 건, 여러 건 지정");
				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);				
				Root<Member> m = cq.from(Member.class);
								
				/*
				 * SELECT에 조회 대상을 하나만 지정.
				 */
				// cq.select(m);
				
				/*
				 * 조회 대상을 여러 건을 지정.
				 * cq.multiselect(cb.array(m.get("userName"), m.get("age")) );
				 * cb.array를 사용해도 된다.
				 */
				cq.multiselect(m.get("userName"), m.get("age"));				
				
				em.createQuery(cq).getResultList().stream().forEach(q -> {
					String userName = (String)q[0];
					Integer age = (Integer)q[1];
					
					System.out.println(String.format("member username : %s, member age : %s"
						, userName
						, age ));
				});
				subPrint.subEndPrint();

				
				
				/*
				 * distinct는 select, multiselect 다음에 distinct(true)를 사용하면 된다.
				 */
				subPrint.subStartPrint("DISTINCT");
				cq.multiselect(m.get("userName"), m.get("age")).distinct(true);				
				
				em.createQuery(cq).getResultList().stream().forEach(q -> {
					String userName = (String)q[0];
					Integer age = (Integer)q[1];
					
					System.out.println(String.format("member username : %s, member age : %s"
						, userName
						, age ));
				});
				subPrint.subEndPrint();

				
				
				/*
				 * JPQL에서 select new 생성자() 구문을 Crieria에서는 cb.construct(클래스 타입, ....)로 사용한다.
				 * JPQL에서는 풀패키지명을 다적어 주었지만 Criteria는 코드를 직접 다루므로 MemberDTO.class처럼
				 * 간략하게 사용할 수 있다.
				 */
				subPrint.subStartPrint("NEW, construct()");
				CriteriaQuery<MemberDTO> memberDtoCq = cb.createQuery(MemberDTO.class);
				m = memberDtoCq.from(Member.class);
				
				memberDtoCq.select(cb.construct(MemberDTO.class, m.get("userName"), m.get("age") ));

				em.createQuery(memberDtoCq).getResultList().stream().forEach(memberDto -> {
					System.out.println(String.format("member username : %s, member age : %s"
						, memberDto.getUserName()
						, memberDto.getAge() ));
				});
				subPrint.subEndPrint();
				
				
				
				/*
				 * Criteria는 Map과 비슷한 튜플이라는 특별한 반환 객체를 제공한다.
				 * 튜플을 사용하려면 cb.crateTupleQuery 또는 cb.createQuery(Tuple.class)로 Criteria를 생성한다.
				 * 
				 * 튜플은 이름 기반이므로 순서 기반의 Object[]보다 안전하다. 그리고 tuple.getElements() 같은 메소드를
				 * 사용해서 현재 튜플의 별칭과 자바 타입도 조회할 수 있다.
				 * 
				 * 참고
				 * 	튜플에 별칭을 준다고 해서 실제 SQL에 별칭이 달리는 것은 아니다. 튜플은 Map과 비슷한 구조여서
				 * 	별칭을 키로 사용한다.
				 */
				subPrint.subStartPrint("튜플");
				CriteriaQuery<Tuple> tupleCq = cb.createTupleQuery();
				m = tupleCq.from(Member.class);
				
				tupleCq.multiselect(
					/*
					 * 튜플은 튜플의 검색 키로 사용할 튜플 전용 별칭을 필수로 할당해야 한다.
					 * 별칭은 alias() 메소드를 사용해서 지정할 수 있다.
					 */
					m.get("userName").alias("name")
					, m.get("age").alias("userAge")
				);
				
				em.createQuery(tupleCq).getResultList().stream().forEach(t -> {
					/*
					 * 선언해둔 튜플 별칭으로 데이터를 조회할 수 있다.
					 */
					String userName = t.get("name", String.class);
					Integer age = t.get("userAge", Integer.class);

					System.out.println(String.format("member username : %s, member age : %s"
						, userName
						, age ));
				});
				
				/*
				 * 튜플과 엔티티 조회
				 * 튜플은 엔티티도 조회할 수 있다. 튜플을 사용할 때는 별칭을 필수로 주어야 하는 것에 주의하자.
				 */
				// cp.multiselect(...) 대신에 tupleCq.select(cb.tuple(...))를 사용 했는데 둘은 같은 기능을 한다.
				tupleCq.select(cb.tuple(
					m.alias("m")
					, m.get("userName").alias("name")
				));
					
				em.createQuery(tupleCq).getResultList().stream().forEach(t -> {
					Member member = t.get("m", Member.class);
					String userName = t.get("name", String.class);

					System.out.println(String.format("member username : %s, member age : %s"
						, userName
						, member.getAge() ));
				});				
				subPrint.subEndPrint();
				
				print.mainEndPrint();
			})
			.start();
	}

}

