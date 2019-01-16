package jpabook.start.valueType.embeddedType;

import java.util.Date;
import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.valueType.embeddedType.entity.Member;
import jpabook.start.valueType.embeddedType.entity.embedded.Address;
import jpabook.start.valueType.embeddedType.entity.embedded.Period;

public class EmbeddedTypeMain {

	/*
	 * 임베디드 타입(복합 값 타입)
	 * 
	 * 새로운 값 타입을 직정 정의해서 사용할 수 있는데, JPA에서는 이것을 임베디드 타입(embedded type)이라 한다.
	 * 중요한 것은 직접 정의한 임베디드 타입도 int, String처럼 값 타입이라는 것이다.
	 * 
	 * 임베디드 타입을 사용하려면 2가지 어노테이션이 필요하다. 참고로 둘 중 하나는 생략해도 된다.
	 * - @Embeddable : 값 타입을 정의하는 곳에 표시
	 * - @Embedded : 값 타입을 사용하는 곳에 표시.
	 * 
	 * 임베디드 타입은 기본 생성자가 필수다.
	 * 
	 * 임베디드 타입 덕분에 객체와 테이블을 아주 세밀하게 매핑하는 것이 가능하다.
	 * 잘 설계한 ORM 애플리케이션은 매핑한 테이블의 수보다 클래스의 수가 더 많다.
	 */
	public static void main(String[] args) {

		Print print = new Print();
		
		new Logic()
			.logic((em, tx) -> {
				print.mainStartPrint("임베디드 타입 저장");
				tx.begin();
				
				Address address = new Address("sinnake city", "sinnake street", "sinnake zipCode");
				Period period = new Period(new Date(), new Date());
				
				Member member = new Member("sinnake", period, address);

				em.persist(member);
				
				tx.commit();
				print.mainEndPrint();
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("임베디드 타입 조회");

				Member member = Optional.ofNullable(em.find(Member.class, 1L)).orElseGet(Member::new);				
				Period period = Optional.ofNullable(member.getPeriod()).orElseGet(Period::new);
				Address address = Optional.ofNullable(member.getAddress()).orElseGet(Address::new);
				
				System.out.println(String.format("memberId : %s, memberName : %s"
					, Optional.ofNullable(member.getId()).orElse(-1L)
					, Optional.ofNullable(member.getName()).orElse("") ));
				
				System.out.println(String.format("periodStartDate : %s, periodEndDate : %s, isWork Method : %s"
					, Optional.ofNullable(period.getStartDate()).orElseGet(Date::new)
					, Optional.ofNullable(period.getEndDate()).orElseGet(Date::new)
					, period.isWork(new Date()) ));
				
				System.out.println(String.format("addressCity : %s, addressStreet : %s, addressZipCode : %s"
					, Optional.ofNullable(address.getCity()).orElse("")
					, Optional.ofNullable(address.getStreet()).orElse("")
					, Optional.ofNullable(address.getZipCode()).orElse("") ));
				
				print.mainEndPrint();
			})
			.start();
	}

}

