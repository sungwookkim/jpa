package jpabook.start.onetomany.bidirectional;

import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.onetomany.bidirectional.entity.Member;
import jpabook.start.onetomany.bidirectional.entity.Team;


public class OneToManyBiMain {
	
	/*
	 * 일대다 양방향[1:N, N:1]
	 * 
	 * 일대다 양방향은 매핑은 존재하지 않는다. 대신 다대일 양방향 매핑을 사용해야 한다.
	 * (일대다 양방향과 다대일 양방향은 사실 똑같은 말이다. 여기서는 왼쪽을 연관관계의 주인으로
	 * 가정해서 분류 했다. 예르 들어 다대일이면 다(N)가 연관관계의 주인이다.)
	 * 더 정확히 말하면 양방향 매핑에서 @OneToMany는 연관관계의 주인이 될 수 없다. 왜냐하면
	 * 관계형 데이터베이스의 특성상 일대다, 다대일 관계는 항상 다 쪽에 외래 키가 있다.
	 * 따라서 @OneToMany, @ManyToOne 둘 중에 연관관계의 주인은 항상 다 쪽인 @ManyToOne을 사용한 곳이다.
	 * 이런 이유로 @ManyToOne에는 mappedBy 속성이 없다.
	 * 
	 * 일대다 단방향 매핑 반대편에 같은 외래 키를 사용하는 다대일 단방향 매핑을 읽기 전용으로 하나 추가하면 된다.
	 * 둘 다 같은 키를 관리하므로 문제가 발생할 수 있다. 따라서 반대편인 다대일 쪽은 insertable = false, updatable = false로 
	 * 설정해서 읽기만 가능하게 했다.
	 * 
	 * 이 방법은 일대다 양방향 매핑이라기보다는 일대다 단방향 매핑 반대편에 다대일 단방향 매핑을 읽기 전용으로
	 * 추가해서 일대다 양방향처럼 보이도록 하는 방법이다. 따라서 일대다 단방향 매핑이 가지는 단점을 가진다.
	 * 될 수 있으면 다대일 양방향 매핑을 사용하자.
	 * 
	 */
	public static void main(String args[]) {
		
		Print print = new Print();
		
		new Logic()
			.logic(em -> {
				Member member1 = new Member("sinnake1", "신나게1");
				Member member2 = new Member("sinnake2", "신나게2");
				
				Team team1 = new Team("team1");
				team1.addMember(member1);
				team1.addMember(member2);
				
				em.persist(member1);
				em.persist(member2);
				em.persist(team1);				
			})
			.start();
		

		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("팀 ID가 1인 회원");
				
				Team team = Optional.ofNullable(em.find(Team.class, new Long(1))).orElse(new Team());
				team.getMembers()
					.stream().forEach(m -> {
						System.out.println(String.format("팀명 : %s, 회원 ID : %s, 회원 이름 : %s"
							, Optional.ofNullable(team.getName()).orElse("")
							, Optional.ofNullable(m.getMemberId()).orElse("")
							, Optional.ofNullable(m.getUsername()).orElse("") ));
					} );
				
				print.mainEndPrint();				
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("회원 ID가 1인 회원의 팀 정보");
				
				Member member = Optional.ofNullable(em.find(Member.class, new Long(1))).orElse(new Member());
				System.out.println(String.format("팀명 : %s, 회원 ID : %s, 회원 이름 : %s"
					, Optional.ofNullable(member.getTeam().getName()).orElse("")
					, Optional.ofNullable(member.getMemberId()).orElse("")
					, Optional.ofNullable(member.getUsername()).orElse("") ));
				
				print.mainEndPrint();				
			})
			.start();
	}
}
