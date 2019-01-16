package jpabook.start.objectOrientedQuery.jpql.fetchJoin;

import java.util.List;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;
import jpabook.start.objectOrientedQuery.entity.Team;

public class FetchJoinMain extends DataInit {
	/*
	 * 페치 조인
	 * 
	 * 페치(fetch) 조인은 SQL에서 이야기하는 조인의 종류는 아니고 JPQL에서 성능 최적화를
	 * 위해 제공하는 기능이다.
	 * 이것은 연관된 엔티티나 컬렉션을 한 번에 같이 조회하는 기능인데 join fetch 명령어로 사용할 수 있다.
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		Print subPrint = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("페치 조인");

				/*
				 * 엔티티 페치 조인
				 * 
				 * 일반적인 JPQL 조인과는 다르게 m.team 다음에 별칭이 없는데 페치 조인은 별칭을 사용할 수 없다.
				 * 
				 * 엔티티 간에 지연로딩으로 설정을 해놨어도 회원을 조회할 때 페치 조인을 사용해서
				 * 팀도 함께 조회했으므로 연관된 팀 엔티티는 프록시가 아닌 실제 엔티티다.
				 * 따라서 연관된 팀을 사용해도 지연 로딩이 일어나지 않는다.
				 * 그리고 프록시가 아닌 실제 엔티티이므로 회원 엔티티가 영속성 컨텍스트에서 분리되어
				 * 준영속 상태가 되어도 연관된 팀을 조회할 수 있다.
				 */
				subPrint.subStartPrint("엔티티 페치 조인");
				List<Member> entityFetchJoin = em.createQuery("SELECT m"
						+ " FROM CH10_OOQ_MEMBER m JOIN FETCH m.team", Member.class)
				.getResultList();
				
				entityFetchJoin.stream().forEach(e -> {
					System.out.println(String.format("member id : %s, member uaerName : %s, member age : %s, member teamName : %s"
						, e.getId()
						, e.getUserName()
						, e.getAge()
						, e.getTeam().getName() ));
				});
				subPrint.subEndPrint();

				
				
				/*
				 * 컬렉션 페치 조인
				 * 
				 * 일대다 관계인 컬렉션을 페치 조인해보자.
				 * 
				 * 컬렉션을 페치 조인한 JPQL에서 select t로 팀만 선택했는데 팀과 연관된 회원도 함께 조회한 것을
				 * 확인할 수 있다.
				 * 그리고 CH10_OOQ_TEAM 테이블에서 '우리반'는 하나지만 CH10_OOQ_MEMBER 테이블과 조인하면서
				 * 결과가 증가해서 '우리반'이 2건 조회되었다.
				 * 
				 * 그리고 컬렉션 페치 조인 결과 객체에서 collectionFetchJoinTeam 결과의 주소를 보면 같은 객체 2건을
				 * 가지게 된다.
				 * 
				 * 참고
				 * 일대다 조인은 결과가 증가할 수 있지만 일대일, 다대일 조인은 결과가 증가하지 않는다.
				 */
				subPrint.subStartPrint("컬렉션 페치 조인");
				List<Team> collectionFetchJoinTeam = em.createQuery("SELECT t"
					+ " FROM CH10_OOQ_TEAM t JOIN FETCH t.member"
					+ " WHERE t.name = :teamName", Team.class)
				.setParameter("teamName", "우리반")
				.getResultList();
				
				collectionFetchJoinTeam.stream().forEach(c -> {
					System.out.println(String.format("teamName = %s", c.getName() ));
					
					c.getMember().stream().forEach(m -> {
						System.out.println(String.format("-> userName", m.getUserName()) + ", " + m);
					});
				});
				subPrint.subEndPrint();

				
				
				/*
				 * 페치 조인과 DISTINCT
				 * 
				 * SQL의 DISTINCT는 중복된 결과를 제거하는 명령어다. JPQL의 DISTINCT 명령어는 SQL에 DISTINCT를 추가하는 것은
				 * 물론이고 애플리케이션에서 한 번 더 중복을 제거한다.
				 * 
				 * 위 컬렉션 페치 조인은 '우리반'이 중복으로 조회된다. 다음처럼 DISTINCT를 추가해보자.
				 * 추가하면 팀 엔티티의 중복이 제거된 '우리반'이 하나만 조회된다.
				 */
				subPrint.subStartPrint("페치 조인과 DISTINCT");
				collectionFetchJoinTeam = em.createQuery("SELECT DISTINCT t"
					+ " FROM CH10_OOQ_TEAM t JOIN FETCH t.member"
					+ " WHERE t.name = :teamName", Team.class)
				.setParameter("teamName", "우리반")
				.getResultList();
					
				collectionFetchJoinTeam.stream().forEach(c -> {
					System.out.println(String.format("teamName = %s", c.getName() ));
					
					c.getMember().stream().forEach(m -> {
						System.out.println(String.format("-> userName", m.getUserName()) + ", " + m);
					});
				});
				subPrint.subEndPrint();
				
				print.mainEndPrint();
			})
			.start();
		
	}

}

