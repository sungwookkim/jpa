package jpabook.start.unidirectional.manytoone;

import java.util.Optional;
import java.util.function.Supplier;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.unidirectional.manytoone.entity.Member;
import jpabook.start.unidirectional.manytoone.entity.Team;

public class ManyToOneMain {

	public static void main(String[] args) {
		Supplier<Member> initMember = () -> { return new Member("", "", new Team()); };
		
		/*
		 * 멤버 및 팀 저장.
		 */
		new Logic()
			.logic((em, tx) -> {
				System.out.println("=============== 멤버 및 팀 저장 ===============");
				tx.begin();

				Team team = new Team("내팀", "이기는팀 우리팀");				
				em.persist(team);
				
				Member sinnakeMember = new Member("sinnake", "신나게", team);
				Member ssinnakeMember = new Member("ssinnake", "씬나게", team);
				
				em.persist(sinnakeMember);
				em.persist(ssinnakeMember);
				
				tx.commit();
				System.out.println("===============================================");
			})
			.start();
		
		/*
		 * 회원 조회.
		 */
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter((em) -> {
				System.out.println("=============== 회원 조회 ===============");
				Member sinnakeMember = Optional.ofNullable(em.find(Member.class, "sinnake")).orElse(initMember.get());
				
				System.out.println("sinnake 회원 전체 정보 : " + sinnakeMember);
				System.out.println("sinnake 회원 팀 정보 : " + sinnakeMember.getTeam());
				System.out.println("=========================================");
			})
			.start();
		
		/*
		 * jpql 조회.
		 */
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter((em) -> {
				System.out.println("=============== jpql 조회 ===============");
				String jpql = "select m from MANY_TO_ONE_MEMBER m join m.team t where t.name = :teamName";

				em.createQuery(jpql, Member.class)
					.setParameter("teamName", "이기는팀 우리팀")
					.getResultStream().forEach(m -> System.out.println("내팀의 전체 회원 : " + m));
				System.out.println("=========================================");
			})
			.start();
		
		/*
		 * 특정 회원 팀 정보 수정.
		 */
		new Logic(JPA_AUTO.UPDATE)
			.logic((em, tx) -> {
				System.out.println("=============== 특정 회원 팀 정보 수정 ===============");
				tx.begin();
				
				Team team = new Team("남팀", "졌으니깐 남의 팀");
				em.persist(team);

				Optional.ofNullable(em.find(Member.class, "ssinnake"))
					.orElse(initMember.get())
					.setTeam(team);
				
				tx.commit();
				System.out.println("======================================================");
			})
			.start();

		/*
		 * 팀 정보 수정된 회원 조회.
		 */
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter((em) -> {
				System.out.println("=============== 팀 정보 수정된 회원 조회 ===============");
				System.out.println("남팀 조회 : " + Optional.ofNullable(em.find(Member.class, "ssinnake")).orElse(initMember.get()) );
				System.out.println("========================================================");
			})
			.start();
		
		/*
		 * 멤버에 팀 연관 관계 제거 및 팀 제거.
		 */
		new Logic(JPA_AUTO.UPDATE)
			.logic((em, tx) -> {
				System.out.println("=============== 멤버에 팀 연관 관계 제거 및 팀 제거 ===============");
				tx.begin();

				Team team = em.find(Team.class, "내팀");
				
				/*
				 * 각 회원에 팀 연관 관계 제거.
				 */
				Optional.ofNullable(em.find(Member.class, "sinnake"))
					.orElse(initMember.get())
					.setTeam(null);
				
				/*
				 * 제거된 팀 삭제.
				 * (외래키로 잡혀 있는게 있으면 삭제가 안되므로 위에서 처럼 외래키를 제거한 후 삭제.)
				 */
				Optional.ofNullable(team)
					.ifPresent(t -> {
						em.remove(t);
					});
				
				tx.commit();
				System.out.println("===================================================================");
			})
			.start();
	}

}
