package jpabook.start.onetoone.mainTable.bidirectional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.onetoone.mainTable.bidirectional.entity.Locker;
import jpabook.start.onetoone.mainTable.bidirectional.entity.Member;

public class OneToOne_BiMain {

	/*
	 * 일대일[1:1]
	 * 
	 * 주 테이블에 외래 키 양방향
	 */
	public static void main(String[] args) {
		Print print = new Print();
		
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
				print.mainStartPrint("회원, 사물함 저장");

				tx.begin();
				
				lockerList.stream().forEach(l -> em.persist(l));
				memberList.stream().forEach(m -> em.persist(m));
				
				lockerList.get(0).setMember(memberList.get(0));
				lockerList.get(1).setMember(memberList.get(1));
				lockerList.get(2).setMember(memberList.get(2));

				tx.commit();
				
				print.mainEndPrint();
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("sinnake1 회원의 사물함 조회");
				
				Member member = Optional.ofNullable(em.createQuery("select m from CH06_ONE_TO_ONE_MAIN_BI_MEMBER m where m.userId = 'sinnake1'", Member.class)
					.getResultList())
				.filter(m -> m.size() > 0)
				.map(m -> m.get(0))
				.orElse(new Member() );
				
				System.out.println(String.format("회원 ID : %s, 사물함 이름 : %s"
					, Optional.ofNullable(member.getUserId()).orElse("")
					, Optional.ofNullable(member.getLocker()).map(Locker::getName).orElse("") ));

				print.mainEndPrint();

				
				
				print.mainStartPrint("locker3인 회원 ID 조회");

				Locker locker = Optional.ofNullable(em.createQuery("select l from CH06_ONE_TO_ONE_MAIN_BI_LOCKER l where name = 'locker3'", Locker.class)
					.getResultList())
				.filter(l -> l.size() > 0)
				.map(l -> l.get(0))
				.orElse(new Locker() );
								
				System.out.println(String.format("locker3 ID : %s, locker3인 회원 ID : %s"
					, Optional.ofNullable(locker.getId()).map(String::valueOf).orElse("") 
					, Optional.ofNullable(locker.getMember()).map(Member::getUserId).orElse("") ));
				
				print.mainEndPrint();
			})
			.start();
	}
}
