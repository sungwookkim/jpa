package jpabook.start.objectOrientedQuery.jpql.setOrder;

import java.util.List;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.objectOrientedQuery.jpql.JpqlCommon;
import jpabook.start.objectOrientedQuery.jpql.entity.Member;

public class SetOrderMain extends JpqlCommon {

	/*
	 * 집합과 정렬
	 * 
	 * 집합은 집합함수와 함께 통계 정보를 구할 때 사용한다.
	 * 
	 * 집합 함수표
	 * COUNT : 결과 수를 반환한다. 반환타입 : Long
	 * MAX, MIN : 최대, 최소 값을 구한다. 문자, 숫자, 날짜 등에 사용한다.  
	 * AVG : 평균값을 구한다. 숫자타입만 사용할 수 있따. 반환타입 : Double
	 * SUM : 합을 구한다. 숫자타입만 사용할 수 있다. 
	 * 	반환 타입 : 
	 * 		정수합 : Long
	 * 		소수합 : Double
	 * 		BigInteger합 : BigInteger
	 * 		BigDecimal합 : BigDecimal
	 * 
	 * 집합 함수 사용 시 참고사항
	 * ㅁNULL 값은 무시하므로 통계에 잡히지 않는다(DISTINCT가 정의되어 있어도 무시된다).
	 * ㅁ만약 값이 없는데 SUM, AVG, MAX, MIN 함수를 사용하면 NULL 값이 된다. 단 COUNT는 0이 된다.
	 * ㅁDISTINCT를 집합 함수 안에 사용해서 중복된 값을 제거하고 나서 집합을 구할 수 있다.
	 * 	예 select COUNT( DISTINCT m.age) from Member m
	 * ㅁ DISTINCT를 COUNT에서 사용할 때 임베디드 타입은 지원하지 않는다.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		initSave();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				System.out.println("=============== GROUP BY, HAVING ===============");
				// 팀 이름을 기준으로 그룹별로 묶어서 통계 데이터 조회.
				List<SetDTO> setDto = em.createQuery("SELECT "
					+ "new jpabook.start.objectOrientedQuery.jpql.setOrder.SetDTO"
					+ "(t.name, COUNT(m.age), SUM(m.age), AVG(m.age), MAX(m.age), MIN(m.age))"
					+ "FROM CH10_OOQ_MEMBER m "
					+ "LEFT JOIN m.team t "
					+ "GROUP BY t.name", SetDTO.class)
				.getResultList();

				setDto.stream().forEach(s -> {
					System.out.println(String.format("name : %s, avg : %s, count : %s, max : %s, sum : %s, min : %s"
						, s.getName()
						, s.getAvg()
						, s.getCount()
						, s.getMax()
						, s.getSum()
						, s.getMin() ));
				});

				// 위 그룹별 통계 데이터 중에서 평균나이가 37살 이상인 그룹을 조회.
				setDto = em.createQuery("SELECT "
					+ "new jpabook.start.objectOrientedQuery.jpql.setOrder.SetDTO"
					+ "(t.name, COUNT(m.age), SUM(m.age), AVG(m.age), MAX(m.age), MIN(m.age))"
					+ "FROM CH10_OOQ_MEMBER m "
					+ "LEFT JOIN m.team t "
					+ "GROUP BY t.name "
					+ "HAVING AVG(m.age) >= 37 "
					, SetDTO.class)
				.getResultList();
				
				setDto.stream().forEach(s -> {
					System.out.println(String.format("name : %s, avg : %s, count : %s, max : %s, sum : %s, min : %s"
						, s.getName()
						, s.getAvg()
						, s.getCount()
						, s.getMax()
						, s.getSum()
						, s.getMin() ));
				});
				System.out.println("================================================");
				
				System.out.println("=============== Order by ===============");
				/*
				 * ORDER BY는 결과를 정렬하 때 사용한다. 다음은 나이를 기준으로 내림차순으로 정렬하고
				 * 나이가 같으면 이름을 기준으로 오름차순으로 정렬한다.
				 */
				List<Member> memberOrderByQuery = em.createQuery("SELECT m "
					+ "from CH10_OOQ_MEMBER m "
					+ "ORDER BY m.age DESC"
					+ ", m.userName ASC", Member.class)
					.getResultList();

				memberOrderByQuery.stream().forEach(m -> {
					System.out.println(String.format("member userName : %s, member age : %s, member id : %s"
						, m.getUserName()						
						, m.getAge()
						, m.getId() ));
				});
				
				List<Object[]> query = em.createQuery("SELECT t.name, COUNT(m.age) as cnt "
					+ "FROM CH10_OOQ_MEMBER m LEFT JOIN m.team t "
					+ "GROUP BY t.name ORDER BY cnt")
					.getResultList();
				
				query.stream().forEach(q -> {
					System.out.println(String.format("team Name : %s, ageCount : %s"
						, (String)q[0]
						, (Long)q[1] ));
				});
				System.out.println("========================================");
			})
			.start();
	}

}
