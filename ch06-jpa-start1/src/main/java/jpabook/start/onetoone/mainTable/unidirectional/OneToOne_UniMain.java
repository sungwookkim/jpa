package jpabook.start.onetoone.mainTable.unidirectional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.onetoone.mainTable.unidirectional.entity.Locker;
import jpabook.start.onetoone.mainTable.unidirectional.entity.Member;

public class OneToOne_UniMain {

	public static void main(String[] args) {
		List<Member> memberList = Arrays.asList(
			new Member("sinnake1")
			, new Member("sinnake2")
			, new Member("sinnake3")
		);
				
		List<Locker> lockerList = Arrays.asList(
			new Locker("locker1")
			, new Locker("locker2")
			, new Locker("locker3")
		);
		
		new Logic()
			.logic((em, tx) -> {
				System.out.println("=============== 회원, 사물함 저장 ===============");
				tx.begin();
				
				lockerList.stream().forEach(l -> em.persist(l));
				memberList.stream().forEach(m -> em.persist(m));
				
				memberList.get(0).setLocker(lockerList.get(0));
				memberList.get(1).setLocker(lockerList.get(1));
				memberList.get(2).setLocker(lockerList.get(2));
				
				tx.commit();
				System.out.println("=================================================");
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.logic(em -> {
				System.out.println("=============== sinnake1 회원의 사물함 조회 ===============");
				
				Member member = Optional.ofNullable(em.createQuery("select m from CH06_ONE_TO_ONE_MAIN_UNI_MEMBER m where m.userId = 'sinnake1'", Member.class)
					.getResultList())
				.filter(m -> m.size() > 0)
				.map(m -> m.get(0))
				.orElse(new Member() );
				
				System.out.println(String.format("회원 ID : %s, 사물함 이름 : %s"
					, Optional.ofNullable(member.getUserId()).orElse("")
					, Optional.ofNullable(member.getLocker()).map(Locker::getName).orElse("") ));

				System.out.println("==========================================================");

				System.out.println("=============== locker2 사물함인 회원 ID 조회 ===============");
				
				member = Optional.ofNullable(em.createQuery("select m from CH06_ONE_TO_ONE_MAIN_UNI_MEMBER m where m.locker.name = 'locker2'", Member.class)
					.getResultList())
				.filter(m -> m.size() > 0)
				.map(m -> m.get(0))
				.orElse(new Member() );
				
				System.out.println(String.format("locker2인 회원 ID : %s, locker2 ID : %s"
					, Optional.ofNullable(member.getUserId()).orElse("") 
					, Optional.ofNullable(member.getLocker()).map(Locker::getId).map(String::valueOf).orElse("") ));

				System.out.println("=============================================================");
			})
			.start();
	}
}
