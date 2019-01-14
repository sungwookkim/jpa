package jpabook.start.objectOrientedQuery.jpql.projection;

import java.util.List;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.objectOrientedQuery.jpql.JpqlCommon;
import jpabook.start.objectOrientedQuery.jpql.entity.Member;
import jpabook.start.objectOrientedQuery.jpql.entity.Product;
import jpabook.start.objectOrientedQuery.jpql.entity.Team;
import jpabook.start.objectOrientedQuery.jpql.entity.embedded.Address;

public class ProjectionMain extends JpqlCommon {

	/*
	 * 프로젝션
	 * 
	 * SELECT 절에 조회할 대상을 지정하는 것을 프로젝션(projection)이라 하고 [SELECT {프로젝션 대상} FROM]으로 대상을 선택한다.
	 * 프로젝션 대상은 엔티티, 엠비디드 타입, 스칼라 타입이 있다.
	 * 스칼라 타입은 숫자, 문자 등 기본 데이터 타입을 뜻한다. 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		initSave();

		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				/*
				 * 엔티티 프로젝션
				 * 
				 * 아래 JPQL에서 처음은 회원을 조회했고 두 번째는 회원과 연관된 팀을 조회했는데 둘 다 엔티티를
				 * 프로젝션 대상으로 사용했다.
				 * 
				 * "조회한 엔티티는 영속성 컨텍스트에서 관리된다."
				 */
				System.out.println("=============== 엔티티 프로젝션 ===============");
				// 회원 대상 프로젝션
				List<Member> entityProjectionMember = em.createQuery("SELECT m FROM CH10_OOQ_MEMBER m", Member.class)
					.getResultList();
					
				entityProjectionMember.stream().forEach(p -> {
					System.out.println("memberName : " + p.getUserName());
					System.out.println("memberAge : " + p.getAge());
				});
				
				// 회원과 연관된 팀 대상 프로젝션
				List<Team> entityProjectionTeam = em.createQuery("SELECT m.team FROM CH10_OOQ_MEMBER m", Team.class)
					.getResultList();
					
				entityProjectionTeam.stream().forEach(t -> {
					System.out.println("memberTeam : " + t.getName());					
				});
				System.out.println("=========================================================");
				
				/*
				 * 임베디드 타입 프로젝션
				 * 
				 * JPQL에서 임베디드 타입은 엔티티와 거의 비슷하게 사용된다. 
				 * "SELECT a FROM Address a"와 같이 임베디드 타입은 조회의 시작점이 될 수 없다는 제약이 있다.
				 * 
				 * "임베디드 타입은 엔티티 타입이 아닌 값 타입이다. 따라서 직접 조회한 임베디드 타입은 영속성 컨텍스트에 관리되지 않는다."
				 */
				System.out.println("=============== 임베디드 타입 프로젝션 ===============");
				// 아래와 같이 엔티티를 통해서 임베디드 타입을 조회할 수 있다.
				List<Address> embeddedTypeProjectionAddress = em.createQuery("SELECT o.address FROM CH10_OOQ_ORDER o WHERE o.member.userName = :userName", Address.class)
					.setParameter("userName", "sinnake1")
					.getResultList();
	
				embeddedTypeProjectionAddress.stream().forEach(a -> {
					System.out.println("city : " + a.getCity());
					System.out.println("street : " + a.getStreet());
					System.out.println("zipCode : " + a.getZipCode());									
				});
				System.out.println("======================================================");
				
				/*
				 * 스칼라 타입 프로젝션
				 * 
				 * 숫자, 문자, 날짜와 같은 기본 데이터 타입들을 스칼라 타입이라고 한다.
				 */
				System.out.println("=============== 스칼라 타입 프로젝션 ===============");
				// 전체 회원의 이름을 조회.
				List<String> scalarProjectionUserName = em.createQuery("SELECT userName FROM CH10_OOQ_MEMBER m", String.class)
					.getResultList();
						
				scalarProjectionUserName.stream().forEach(p -> {
					System.out.println("memberName : " + p);
				});
				
				// 아래와 같이 통계 쿼리도 주로 스칼라 타입으로 조회한다.
				Double orderAmountAvg = em.createQuery("SELECT AVG(o.orderAmount) FROM CH10_OOQ_ORDER o", Double.class).getSingleResult();
				System.out.println("평균 치 orderAmount : " + orderAmountAvg);				
				
				/*
				 * 여러 값 조회
				 * 
				 * 엔티티를 대상으로 조회하면 편리하겠지만, 꼭 필요한 데이터들만 선택해서 조회해야 할 때도 있다.
				 * 프로젝션에 여러 값을 선택하면 TypeQuery를 사용할 수 없고 Query를 사용해야 한다.
				 */
				List<Object[]> queryValues = em.createQuery("SELECT m.userName, m.age FROM CH10_OOQ_MEMBER m").getResultList();
				queryValues.stream().forEach(q -> {
					System.out.println("userName : " + (String)q[0]);
					System.out.println("userAge : " + (Integer)q[1]);
				});
				
				// 스칼라 타입뿐 아니라 엔티티 타입도 여러 값을 함께 조회 할 수 있다.(물론 이때도 조회한 엔티티는 영속성 컨텍스트에서 관리된다.)
				queryValues = em.createQuery("SELECT o.member, o.product, o.orderAmount FROM CH10_OOQ_ORDER o WHERE o.member.userName = :userName")
					.setParameter("userName", "sinnake1")
					.getResultList();
	
				queryValues.stream().forEach(q -> {
					Member member = (Member)q[0];
					Product product = (Product)q[1];
					
					System.out.println("userName : " + member.getUserName());
					System.out.println("productName : " + product.getName());
					System.out.println("orderAmount : " + (Integer)q[2]);
				});
				
				/*
				 * NEW 명령어
				 * 
				 * 실무에선 위 처럼 Object[]를 사용하진 않고 아래와 같이 NEW 명령어를 사용해서 반환받을 클래스를 지정할 수 있는데
				 * 이 클래스의 생성자에 JQPL 조회 결과를 넘겨주게 된다.
				 * 
				 * NEW 명령어를 사용할 떄는 다음 2가지를 주의해야 한다.
				 * 1. 패키지 명을 포함한 전체 클래스 명을 입력해야 한다.
				 * 2. 순서와 타입이 일치하는 생성자가 필요하다.
				 */
				List<OrderDTO> orderDTOs = em.createQuery("SELECT "
						+ "new jpabook.start.objectOrientedQuery.jpql.projection.OrderDTO"
						+ "(o.member, o.product, o.orderAmount) "
						+ "FROM CH10_OOQ_ORDER o WHERE o.member.userName = :userName", OrderDTO.class)
					.setParameter("userName", "sinnake1")
					.getResultList();
	
				orderDTOs.stream().forEach(o -> {
					System.out.println("userName : " + o.getMember().getUserName());
					System.out.println("productName : " + o.getProduct().getName());
					System.out.println("orderAmount : " + o.getOrderAmount());
				});
				System.out.println("====================================================");
			})
			.start();
	}
}
