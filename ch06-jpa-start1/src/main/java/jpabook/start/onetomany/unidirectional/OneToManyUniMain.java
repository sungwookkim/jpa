package jpabook.start.onetomany.unidirectional;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.onetomany.unidirectional.entity.Member;
import jpabook.start.onetomany.unidirectional.entity.Team;

public class OneToManyUniMain {
	/*
	 * 일대다 단방향[1:N]
	 * 
	 * 일대다 관계는 다대일 관계의 반대 방향이다. 일대다 관계는 엔티티를 하나 이상 참조할 수 있으므로
	 * 자바 컬렉션인 Collection, List, Set, Map 중에 하나를 사용해야 한다.
	 * 
	 * 일대다 단방향 관계는 JPA 2.0부터 지원한다.
	 * 
	 * 보통 자신이 매핑한 테이블의 외래 키를 관리하는다. 이 매핑은 반대쪽 테이블에 있는 외래 키를 관리한다.
	 * 일대다 관계에서 외래 키는 항상 다쪽 테이블에 있는데 다쪽인 Member 엔티티에는 외래 키를 매핑할 수 있는 참조 필드가 없다.
	 * 대신 반대쪽인 Team 엔티티에만 참조 필드인 members가 있다.
	 * 따라서 반대편 테이블의 외래 키를 관리하는 특이한 모습이 나타난다.
	 * 
	 * 일대다 단방향 관계를 매핑할 때는 @JoinColumn을 명시해야 한다. 그렇지 않으면 JPA는 연결 테이블을 두고 연관관계를
	 * 조인 테이블(JoinTable)전략을 기본으로 사용해서 매핑한다.
	 * 
	 * - 일대다 단방향 매핑의 단점
	 * 매핑한 객체가 관리하는 외래 키가 다른 테이블에 있다는 점이다.
	 * 본인 테이블에 외래 키가 있으면 엔티티의 저장과 연관관계 처리를 INSERT SQL 한 번으로 끝내지만
	 * 다른 테이블에 외래 키가 있으면 연관관계를 처리 하기 위해 UPDATE SQL을 추가로 실행한다.
	 * 
	 * - 일대다 단방향 매핑보다는 다대일 양방향 매핑을 사용하자
	 * 엔티티를 매핑한 테이블이 아닌 다른 테이블의 외래 키를 관리해야 한다.
	 * 이것은 성능 문제도 있지만 관리도 부담이 된다.
	 * 일대다 단방향 대신 다대일 양방향 매핑을 사용한다. 다대일 양방향 매핑은 관리해야 하는 외래 키가
	 * 본인 테이블에 있다.
	 * 따라서 일대다 단방향 매핑 같은 문제가 발생되지 않는다. 두 매핑의 테이블 모양은 완전히 같으므로
	 * 엔티티만 약산 수정하면 된다. 
	 * 상황에 다르지만 일대다 단방향 매핑보다는 다대일 양방향 매핑을 권장한다.
	 */
	public static void main(String args[]) {

		Print print = new Print();
		
		new Logic()
			.logic((em, tx) -> {
				print.mainStartPrint("회원과 팀 설정");

				tx.begin();
				
				Member member1 = new Member("sinnake1", "신나게1");
				Member member2 = new Member("sinnake2", "신나게2");
				
				Team team1 = new Team("team1");
				team1.getMembers().add(member1);
				team1.getMembers().add(member2);
				
				em.persist(member1);
				em.persist(member2);
				em.persist(team1);
				
				tx.commit();
				
				print.mainEndPrint();
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("팀 ID가 1인 회원");
				
				em.find(Team.class, new Long(1)).getMembers()
					.stream().forEach(m -> System.out.println(m.getUsername()) );
				
				print.mainEndPrint();
			})
			.start();
	}
}
