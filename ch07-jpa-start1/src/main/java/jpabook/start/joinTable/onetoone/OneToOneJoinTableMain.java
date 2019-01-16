package jpabook.start.joinTable.onetoone;

import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.joinTable.onetoone.entity.Child;
import jpabook.start.joinTable.onetoone.entity.Parent;

public class OneToOneJoinTableMain {

	/*
	 * 일대일 관계를 만들려면 조인 테이블의 외래 키 컬럼 각각에 총 2개의 유니크 제약조건을 걸어야 한다.
	 * (PARENT_ID는 기본 키이므로 유니크 제약조건이 걸려있다.)
	 */
	public static void main(String[] args) {
		
		Print print = new Print();
		
		new Logic()
			.logic((em, tx) -> {
				print.mainStartPrint("일대일 조인테이블 저장");

				tx.begin();
				
				Child child = new Child("sinnake_child");
				Parent parent = new Parent(child, "sinnake_parent");

				em.persist(child);
				em.persist(parent);

				tx.commit();
				
				print.mainEndPrint();
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				Parent parent = Optional.ofNullable(em.createQuery("select p from CH07_JOINTABLE_ONETOONE_PARENT p where p.id = :id", Parent.class)
					.setParameter("id", 1L)
					.getResultList() )
				.filter(p -> p.size() > 0)
				.map(p -> p.get(0))
				.orElseGet(Parent::new);
				
				Child child = Optional.ofNullable(parent.getChild()).orElseGet(Child::new);
				
				System.out.println(String.format("parent id : %s, parent name : %s, child id : %s, child name : %s"
					, Optional.ofNullable(parent.getId()).orElse(-1L)
					, Optional.ofNullable(parent.getName()).orElse("") 
					, Optional.ofNullable(child.getId()).orElse(-1L)
					, Optional.ofNullable(child.getName()).orElse("") ));
			})
			.start();
	}
}
