package jpabook.start.relationship.compositeKey.nonIdentifying.idClass;

import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.relationship.compositeKey.nonIdentifying.idClass.entity.Child;
import jpabook.start.relationship.compositeKey.nonIdentifying.idClass.entity.Parent;
import jpabook.start.relationship.compositeKey.nonIdentifying.idClass.entity.ParentId;

public class NonIdentifying_IdClassMain {

	public static void main(String[] args) {
		new Logic()
			.logic((em, tx) -> {
				System.out.println("=============== [복합 키 - @IdClass] 비식별 관계 매핑 저장 ===============");
				tx.begin();
				
				Parent parent = new Parent("id1", "id2", "sinnake");
				em.persist(parent);
				
				tx.commit();
				System.out.println("==========================================================================");
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				System.out.println("=============== [복합 키 - @Idclass] 비식별 관계 매핑 조회 ===============");
				
				Parent parent = em.find(Parent.class, new ParentId("id1", "id2"));
				
				System.out.println(String.format("Id1 : %s, Id2 : %s, name : %s"
					, Optional.ofNullable(parent.getId1()).orElse("")
					, Optional.ofNullable(parent.getId2()).orElse("")
					, Optional.ofNullable(parent.getName()).orElse("") ));
				
				System.out.println("==========================================================================");
			})
			.start();

		new Logic(JPA_AUTO.UPDATE)
			.logic((em, tx) -> {
				System.out.println("=============== [복합 키 - @IdClass - 자식 클래스] 비식별 관계 매핑 저장 ===============");
				tx.begin();
				
				Child child = new Child("child-sinnake", new Parent("id1", "id2", "sinnake"));
				em.persist(child);
				
				tx.commit();
				System.out.println("========================================================================================");
			}).start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				System.out.println("=============== [복합 키 - @IdClass - 자식 클래스] 비식별 관계 매핑 조회 ===============");
				
				Child child = Optional.ofNullable(em.createQuery("select c from CH07_COMPOSITEKEY_NONIDENTIFYING_IDCLASS_CHILD c where c.parent.id1 = :id1", Child.class)
					.setParameter("id1", "id1")
					.getResultList())
				.filter(c -> c.size() > 0)
				.map(c -> c.get(0))
				.orElseGet(Child::new);

				Parent parent = Optional.ofNullable(child.getParent()).orElseGet(Parent::new);
				 
				System.out.println(String.format("child ID : %s, child Name : %s, parent Id1 : %s, parent Id2 : %s"
					, Optional.ofNullable(child.getId()).map(String::valueOf).orElse("") 
					, Optional.ofNullable(child.getName()).orElse("")
					, Optional.ofNullable(parent).map(Parent::getId1).orElse("")
					, Optional.ofNullable(parent).map(Parent::getId2).orElse("") ));
								
				System.out.println("========================================================================================");
			}).start();		
	}

}
