package jpabook.start.bidirectional.onetomany;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.bidirectional.onetomany.entity.Member;
import jpabook.start.bidirectional.onetomany.entity.Team;

public class OneToManyMain {

	public static void main(String args[]) {
		Print print = new Print();
		
		/*
		 * 멤버 및 팀 저장.
		 */
		new Logic()
			.logic((em, tx) -> {
				print.mainStartPrint("멤버 및 팀 저장");

				tx.begin();

				Team team1 = new Team("team1", "이기는팀 우리팀");
				em.persist(team1);
				
				Member sinnakeMember = new Member("sinnake1", "신나게1", team1);
				Member sinnake2Member = new Member("sinnake2", "신나게2", team1);
				
				em.persist(sinnakeMember);
				em.persist(sinnake2Member);

				tx.commit();
				
				print.mainEndPrint();
			})
			.start();
		
		/*
		 * 일대다 방향으로 객체 그래프 탐색.
		 */
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("일대다 방향으로 객체 그래프 탐색");
				
				Team team = em.find(Team.class, "team1");

				team.getMember().stream().forEach(m -> {
					System.out.println("team인 회원명 : " + m.getUsername() + ", 팀명 : " + team.getName());
				});
				
				print.mainEndPrint();				
			})
			.start();
	}
}
