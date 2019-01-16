package jpabook.start.onetoone.targetTable.bidirectional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.onetoone.targetTable.bidirectional.entity.Locker;
import jpabook.start.onetoone.targetTable.bidirectional.entity.Member;

public class OneToOne_BiMain {

	/*
	 * 일대일[1:1]
	 * 
	 * 대상 테이블에 외래 키
	 * 
	 * 단방향
	 * 대상 테이블에 외래 키가 있는 단방향 관계는 JPA에서 지원하지 않는다.
	 * 더불어 이런 모양으로 매핑할 수 있는 방법도 없다.
	 * 이때는 단방향 관계를 Locker에서 Member 방향으로 수정하거나, 양방향 관계로 만들고
	 * Locker를 연관관계의 주인으로 설정해야 한다.
	 * 참고로 JPA 2.0부터 일대다 단방향 관계에서 대상 테이블에 외래 키가 있는 매핑을 허용했다.
	 * 하지만 일대일 단방향은 이런 매핑을 허용하지 않는다.
	 * 
	 * 양방향
	 * 프록시를 사용할 때 외래 키를 직접 관리하지 않는 일대일 관계는 지연 로딩으로 설정해도 즉시 로딩된다.
	 * 아래 예제에서 Locker.member는 지연 로딩될 수 있지만, Member.locker는 지연 로딩으로 설정해도 즉시 로딩된다.
	 * 이것은 프록시의 한계 때문에 발생하는 문제인데 프록시 대신에 bytecode instrumentation을 사용하면 해결할 수 있다.
	 * 
	 * 해결책은 아래 URL를 참고하자. 
	 * https://developer.jboss.org/wiki/SomeExplanationsOnLazyLoadingone-to-one 
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
					print.mainStartPrint("sinnake2 회원의 사물함 조회");					

					Locker locker = Optional.ofNullable(em.createQuery("select l from CH06_ONE_TO_ONE_TARGET_BI_LOCKER l where l.member.userId = 'sinnake2'", Locker.class)
						.getResultList())
					.filter(l -> l.size() > 0)
					.map(l -> l.get(0))
					.orElse(new Locker() );
					
					System.out.println(String.format("회원 ID : %s, locker 이름 : %s"
						, Optional.ofNullable(locker.getMember()).map(Member::getUserId).orElse("")
						, Optional.ofNullable(locker.getId()).map(String::valueOf).orElse("") ));
					
					print.mainEndPrint();
					
					
					
					print.mainStartPrint("locker2를 사용하는 회원 ID 조회");					

					locker = Optional.ofNullable(em.createQuery("select l from CH06_ONE_TO_ONE_TARGET_BI_LOCKER l where l.name = 'locker2'", Locker.class)
						.getResultList())
					.filter(l -> l.size() > 0)
					.map(l -> l.get(0))
					.orElse(new Locker() );
					
					System.out.println(String.format("회원 ID : %s, locker 이름 : %s"
						, Optional.ofNullable(locker.getMember()).map(Member::getUserId).orElse("")
						, Optional.ofNullable(locker.getName()).orElse("") ));
					
					print.mainEndPrint();
				})
				.start();
	}

}
