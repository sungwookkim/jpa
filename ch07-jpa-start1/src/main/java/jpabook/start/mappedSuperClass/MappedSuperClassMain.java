package jpabook.start.mappedSuperClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.util.Logic;
import common.util.Print;
import jpabook.start.mappedSuperClass.entity.Member;
import jpabook.start.mappedSuperClass.entity.Seller;

public class MappedSuperClassMain {
	/*
	 * @MappedSuperClass
	 * 
	 * 부모 클래스는 테이블과 매핑하지 않고 부모 클래스를 상속 받는 자식 클래스에게 매핑 정보만 제공하고 싶으면
	 * @MappedSuperClass를 사용하면 된다.
	 * @MappedSuperClass는 추상 클래스와 비슷한데 @Entity는 실제 테이블과 매핑되지만 @MappedSuperClass는 실제 테이블과는
	 * 매핑되지 않는다.
	 * 이것은 단순히 매핑 정보를 상속할 목적으로만 사용된다.
	 * 
	 * 부모로부터 물려받은 매핑 정보를 재정의하려면 @AttributeOverrides나 @@AttributeOverride를 사용하고,
	 * 연관관계를 재정의하려면 @AssociationOverrides나 @AssociationOverride를 사용한다.
	 * 
	 * @MappedSuperClass를 사용하면 등록일자, 수정일자, 등록자, 수정자 같은 여러 엔티티에서 공통으로 사용하는 속성을
	 * 효과적으로 관리할 수 있다.
	 * 
	 * 특징
	 * - 테이블과 매핑되지 않고 자식 클래스에 엔티티의 매핑 정보를 상속하기 위해 사용한다.
	 * - @MappedSuperClass로 지정한 클래스는 엔티티가 아니므로 em.find나 JPQL에서 사용할 수 없다.
	 * - 이 클래스를 직접 생성해서 사용할 일은 거의 없으므로 추상 클래스로 만드는 것을 권장한다. 
	 */
	
	public static void main(String[] args) {

		Print print = new Print();
		
		new Logic()
			.logic((em, tx) -> {
				print.mainStartPrint("회원, 셀러 저장");

				tx.begin();
				
				em.persist(new Member("sinnake", "rnsqka2000@gmail.com"));
				em.persist(new Seller("sinnake", "sinnake Shop"));				

				tx.commit();
				
				print.mainEndPrint();
			})
			.commitAfter(em -> {
				print.mainStartPrint("회원 조회");

				List<Member> members = Optional.ofNullable(em.createQuery("select m from CH07_MAPPED_SUPER_CLASS_MEMBER m where m.name = :name", Member.class)
					.setParameter("name", "sinnake")
					.getResultList())
				.filter(m -> m.size() > 0)
				.orElseGet(ArrayList::new);

				members.stream().forEach(m -> {
					System.out.println(String.format("ID : %s, 회원 ID : %s, 이메일 : %s"
						, Optional.ofNullable(m.getId()).map(String::valueOf).orElse("")
						, Optional.ofNullable(m.getName()).orElse("")
						, Optional.ofNullable(m.getEmail()).orElse("") ));
				});
				
				print.mainEndPrint();
				
				
				
				print.mainStartPrint("셀러 조회");

				List<Seller> sellers = Optional.ofNullable(em.createQuery("select s from CH07_MAPPED_SUPER_CLASS_SELLER s where s.name = :name", Seller.class)
					.setParameter("name", "sinnake")
					.getResultList())
				.filter(s -> s.size() > 0)
				.orElseGet(ArrayList::new);

				sellers.stream().forEach(s -> {
					System.out.println(String.format("ID : %s, 셀러 ID : %s, 샵 이름 : %s"
						, Optional.ofNullable(s.getId()).map(String::valueOf).orElse("")
						, Optional.ofNullable(s.getName()).orElse("")
						, Optional.ofNullable(s.getShopName()).orElse("") ));
				});
				
				print.mainEndPrint();
			})
			.start();

	}

}

