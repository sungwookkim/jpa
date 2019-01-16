package jpabook.start.relationship.nonIdentifying;

import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.relationship.nonIdentifying.entity.Child;
import jpabook.start.relationship.nonIdentifying.entity.GrandChild;
import jpabook.start.relationship.nonIdentifying.entity.Parent;

public class NonIdentifyingMain {

	/*
	 * 식별 관계의 복합 키를 사용한 코드와 비교하면 매핑도 쉽고 코드도 단순하다.
	 * 그리고 복합 키가 없으므로 복합 키 클래스를 만들지 않아도 된다.
	 */
	public static void main(String[] args) {
		
		Print print = new Print();
		
		new Logic()
			.logic((em, tx) -> {
				print.mainStartPrint("비식별 관계 매핑 저장");

				tx.begin();
				
				Parent parent = new Parent("sinnake_parent_id", "sinnake_parent_name");
				Child child = new Child(parent, "sinnake_child_id", "sinnake_child_name");
				GrandChild grandChild = new GrandChild(child, "sinnake_grandChild_id", "sinnake_grandChild_name");
				
				em.persist(parent);
				em.persist(child);
				em.persist(grandChild);
				
				tx.commit();
				
				print.mainEndPrint();
			})
			.start();

		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("[Parent] 비식별 관계 매핑 조회");

				Parent parent = Optional.ofNullable(em.createQuery("select p from CH07_NONIDENTIFYING_PARENT p where p.parentId = :parentId", Parent.class)
					.setParameter("parentId", "sinnake_parent_id")
					.getResultList() )
				.filter(p -> p.size() > 0)
				.map(p -> p.get(0))
				.orElseGet(Parent::new);
				
				System.out.println(String.format("parent_id : %s, parentId : %s, parentName : %s"
					, Optional.ofNullable(parent.getId()).map(String::valueOf).orElse("-1") 
					, Optional.ofNullable(parent.getParentId()).orElse("")
					, Optional.ofNullable(parent.getName()).orElse("") ));
				
				print.mainEndPrint();
				
				
				
				print.mainStartPrint("[Child] 비식별 관계 매핑 조회");

				Child child = Optional.ofNullable(em.createQuery("select c from CH07_NONIDENTIFYING_CHILD c where c.parent.id = :parent_id", Child.class)
					.setParameter("parent_id", Optional.ofNullable(parent.getId()).orElse(-1L) )
					.getResultList() )
				.filter(c -> c.size() > 0)
				.map(c -> c.get(0))
				.orElseGet(Child::new);

				System.out.println(String.format("parent_id : %s, child_id : %s, childId : %s, childName : %s"
					, Optional.ofNullable(child.getParent()).map(Parent::getId).map(String::valueOf).orElse("-1")
					, Optional.ofNullable(child.getId()).map(String::valueOf).orElse("-1")
					, Optional.ofNullable(child.getChildId()).orElse("")
					, Optional.ofNullable(child.getName()).orElse("") ));

				print.mainEndPrint();
				
				
				
				print.mainStartPrint("[GrandChild] 비식별 관계 매핑 조회");

				GrandChild grandChild = Optional.ofNullable(em.createQuery("select g from CH07_NONIDENTIFYING_GRANDCHILD g where g.child.id = :child_id", GrandChild.class)
					.setParameter("child_id", Optional.ofNullable(child.getId()).orElse(-1L) )
					.getResultList() )
				.filter(g -> g.size() > 0)
				.map(g -> g.get(0))
				.orElseGet(GrandChild::new);
				
				System.out.println(String.format("child_id : %s, grandChild_id : %s, grandChildId : %s, grandChildName : %s"
					, Optional.ofNullable(grandChild.getChild()).map(Child::getId).map(String::valueOf).orElse("-1")
					, Optional.ofNullable(grandChild.getId()).map(String::valueOf).orElse("-1")
					, Optional.ofNullable(grandChild.getGrandChildId()).orElse("")
					, Optional.ofNullable(grandChild.getName()).orElse("") ));
				
				print.mainEndPrint();									
			})
			.start();
	}
}
