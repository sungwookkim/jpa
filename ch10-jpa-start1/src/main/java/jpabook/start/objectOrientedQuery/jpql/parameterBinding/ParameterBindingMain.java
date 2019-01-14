package jpabook.start.objectOrientedQuery.jpql.parameterBinding;

import java.util.List;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.objectOrientedQuery.jpql.JpqlCommon;
import jpabook.start.objectOrientedQuery.jpql.entity.Member;

public class ParameterBindingMain extends JpqlCommon {

	/*
	 * 파라미터 바인딩
	 * 
	 * JPQL을 수정해서 파라미터 바인딩 방식을 사용하지 않고 직접 문자를 더해 만들어서 넣으면
	 * 악의적인 사용자에 의해 SQL 인젝션 공격을 당할 수 있다.
	 * 또한 성능 이슈도 있는데 파라미터 바인딩 방식을 사용하면 파라미터의 값이 달라도 같은 쿼리로
	 * 인식해서 JPA는 JPQL을 SQL로 파싱한 결과를 재사용할 수 있다.
	 * 그리고 데이터베이스도 내부에서 실행한 SQL을 파싱해서 사용하는데 같은 쿼리는 결과를 재사용할 수 있다.
	 * 결과적으로 애플리케이션과 데이터베이스 모두 해당 쿼리의 파싱 결과를 재사용할 수 있어서 전체 성능이 향상된다.
	 * 따라서 "파라미터 바인딩 방식은 선택이 아닌 필수다"
	 */
	public static void main(String[] args) {
		initSave();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				/*
				 * 이름 기준 파라미터
				 * 이름 기준 파라미터(Named parameters)는 파라미터를 이름으로 구분하는 방법이다.
				 * 이름 기준 파라미터는 앞에 :를 사용한다.
				 */
				System.out.println("=============== 이름 기준 파라미터 바인딩 ===============");
				Member namedParamMember = em.createQuery("SELECT m FROM CH10_OOQ_MEMBER m WHERE m.userName = :userName", Member.class)
					.setParameter("userName", "sinnake1")
					.getSingleResult();
				
				System.out.println("memberName : " + namedParamMember.getUserName());
				System.out.println("memberAge : " + namedParamMember.getAge());
				System.out.println("memberTeam : " + namedParamMember.getTeam().getName());
				System.out.println("=========================================================");
				
				/*
				 * 위치 기준 파라미터
				 * 위치 기준 파라미터(Positional parameters)를 사용하려면 ? 다음에 위치 값을 주면 된다.
				 * 위치 값은 1부터 시작한다.
				 */
				System.out.println("=============== 위치 기준 파라미터 바인딩 ===============");
				List<Member> positionParamMember = em.createQuery("SELECT m FROM CH10_OOQ_MEMBER m WHERE m.team.name = ?1", Member.class)
					.setParameter(1, "우리반")
					.getResultList();
				
				positionParamMember.stream().forEach(p -> {
					System.out.println("memberName : " + p.getUserName());
					System.out.println("memberAge : " + p.getAge());
					System.out.println("memberTeam : " + p.getTeam().getName());					
				});
				System.out.println("=========================================================");
			})
			.start();		

	}

}
