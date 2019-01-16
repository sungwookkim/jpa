package jpabook.start.manytoone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.manytoone.entity.Member;
import jpabook.start.manytoone.entity.Team;

public class ManyToOneMain {

	/*
	 * 다대일
	 * 
	 * 다대일 관계의 반대 방향은 항상 일대다 관계고 일대다 관계의 반대 방향은 항상 다대일 관계다.
	 * 데이터베이스의 테이블의 일(1), 다(N) 관계에서 외래 키는 항상 다쪽에 있다.
	 * 따라서 객체 양방향 관계에서 연관관계의 주인은 항상 다쪽이다.
	 * 
	 * - 양방향은 외래 키가 있는 쪽이 연관관계의 주인이다.
	 * 일대다와 다대일 연관관계는 항상 다(N)에 외래 키가 있다.
	 * JPA는 외래 키를 관리할 때 연관관계의 주인만 사용한다. 주인이 아닌 곳에서는 JPQL이나 객체 그래프를
	 * 탐색할 때 사용한다.
	 * 
	 *  - 양방향 연관관계는 항상 서로를 참조해야 한다.
	 * 양방향 연관관계는 항상 서로 참조해야 한다. 어느 한 쪽만 참조하면 양방향 연관관계가 성립하지 않는다.
	 * 편의 메소드(Member.setTeam, Team.addMember 메소드)는 한 곳에만 작성하거나 양쪽 다 작성할 수 있는데,
	 * 양쪽에 다 작성하면 무한루프에 빠지므로 주의해야 한다.
	 * 
	 */
	public static void main(String[] args) {
		Print print = new Print();
				
		List<Team> teamList = Arrays.asList(
			new Team("team1")
			, new Team("team2")
			, new Team("team3")
		);

		List<Member> memberList = Arrays.asList(
			new Member("sinnake1", "신나게1")
			, new Member("sinnake2", "신나게2")
			, new Member("sinnake3", "신나게3")
			, new Member("sinnake4", "신나게4")
		);
		
		new Logic()
			.logic((em, tx) -> {
				print.mainStartPrint(" 팀, 회원 저장");
				
				tx.begin();			
				
				memberList.get(0).setTeam(teamList.get(0));
				memberList.get(1).setTeam(teamList.get(1));
				
				teamList.get(2).addMember(memberList.get(2));
				teamList.get(2).addMember(memberList.get(3));
				
				teamList.stream().forEach(t -> em.persist(t));
				memberList.stream().forEach(m -> em.persist(m));

				tx.commit();
				
				print.mainEndPrint();
			})
			.start();

		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("sinnake2 회원의 팀 정보");
				
				Member member = Optional.ofNullable(em.createQuery("select m from CH06_MANY_TO_ONE_MEMBER m where m.memberId = 'sinnake2'", Member.class)
							.getResultList())
						.filter(m -> m.size() > 0)
						.map(m -> m.get(0))
						.orElse(new Member());

				Team team = Optional.ofNullable(member.getTeam()).orElse(new Team());
				System.out.println("회원 ID : " + Optional.ofNullable(member.getMemberId()).orElse("")
						+ ", 회원 이름 : " + Optional.ofNullable(member.getUsername()).orElse("")
						+ ", 팀이름 : " + Optional.ofNullable(team.getName()).orElse(""));
				
				print.mainEndPrint();
				
				

				print.mainStartPrint("팀이 team3인 모든 회원");

				List<Team> teams = Optional.ofNullable(em.createQuery("select t from CH06_MANY_TO_ONE_TEAM t where t.name = 'team3'", Team.class)
					.getResultList())
				.filter(m -> m.size() > 0)
				.orElse(new ArrayList<>());
				
				teams.stream()
					.forEach(t -> {
						t.getMembers().stream().forEach(m -> {
							System.out.println("팀 이름 : " + t.getName() 
								+ ", 회원 ID : " + m.getMemberId() 
								+ ", 회원 이름 : " + m.getUsername() );	
						});
					});
				
				print.mainEndPrint();
			})
			.start();

		
	}

}
