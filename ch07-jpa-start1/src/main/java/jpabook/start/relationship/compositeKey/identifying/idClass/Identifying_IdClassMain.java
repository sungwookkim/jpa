package jpabook.start.relationship.compositeKey.identifying.idClass;

import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.relationship.compositeKey.identifying.idClass.entity.Child;
import jpabook.start.relationship.compositeKey.identifying.idClass.entity.ChildId;
import jpabook.start.relationship.compositeKey.identifying.idClass.entity.GrandChild;
import jpabook.start.relationship.compositeKey.identifying.idClass.entity.GrandChildId;
import jpabook.start.relationship.compositeKey.identifying.idClass.entity.Parent;

public class Identifying_IdClassMain {

	public static void main(String[] args) {
		new Logic()
			.logic((em, tx) -> {
				System.out.println("=============== [복합 키 - @IdClass] 식별 관계 매핑 저장 ===============");
				tx.begin();
				
				Parent parent = new Parent("sinnake_parent_id", "sinnake_parent_name");
				Child child = new Child(parent, "sinnake_child_id", "sinnake_child_name");
				GrandChild grandChild = new GrandChild(child, "sinnake_grandChild_id", "sinnake_grandChild_name");
				
				em.persist(parent);
				em.persist(child);
				em.persist(grandChild);
				
				tx.commit();
				System.out.println("========================================================================");
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				System.out.println("=============== [복합 키 - @IdClass] 식별 관계 Parent 조회 ===============");
				
				Parent parent = Optional.ofNullable(em.find(Parent.class, 1L)).orElseGet(Parent::new);

				System.out.println(String.format("parent Id : %s, parent MemberId : %s, parent MemberName : %s"
					, Optional.ofNullable(parent.getId()).map(String::valueOf).orElse("")
					, Optional.ofNullable(parent.getMemberId()).orElse("")
					, Optional.ofNullable(parent.getMemberName()).orElse("") ));
				
				System.out.println("==========================================================================");
				
				System.out.println("=============== [복합 키 - @IdClass] 식별 관계 Child 조회 ===============");

				Child child = Optional.ofNullable(em.find(Child.class, new ChildId(1L, "sinnake_child_id") )).orElseGet(Child::new);
				
				parent = Optional.ofNullable(child.getParent()).orElseGet(Parent::new);
						
				System.out.println(String.format("child Id : %s, child Name : %s, parent Id : %s, parent MemberId : %s, parent MemberName : %s"
					, Optional.ofNullable(child.getChildId()).map(String::valueOf).orElse("")
					, Optional.ofNullable(child.getChildName()).orElse("")
					, Optional.ofNullable(parent.getId()).map(String::valueOf).orElse("")
					, Optional.ofNullable(parent.getMemberId()).orElse("")
					, Optional.ofNullable(parent.getMemberName()).orElse("") ));
				
				System.out.println("=========================================================================");
				
				System.out.println("=============== [복합 키 - @IdClass] 식별 관계 GrandChild 조회 ===============");

				GrandChild grandChild = Optional.ofNullable(em.find(GrandChild.class
					, new GrandChildId(new ChildId(1L, "sinnake_child_id"), "sinnake_grandChild_id") )).orElseGet(GrandChild::new);
				
				child = Optional.ofNullable(grandChild.getChild()).orElseGet(Child::new);
				parent = Optional.ofNullable(child.getParent()).orElseGet(Parent::new);					
				
				System.out.println(String.format("grandChild Id : %s, grandChild name : %s, child Id : %s, child Name : %s, parent Id : %s, parent MemberId : %s, parent MemberName : %s"
					, Optional.ofNullable(grandChild.getId()).orElse("")
					, Optional.ofNullable(grandChild.getGrandChildName()).orElse("")
					, Optional.ofNullable(child.getChildId()).map(String::valueOf).orElse("")
					, Optional.ofNullable(child.getChildName()).orElse("")
					, Optional.ofNullable(parent.getId()).map(String::valueOf).orElse("")
					, Optional.ofNullable(parent.getMemberId()).orElse("")
					, Optional.ofNullable(parent.getMemberName()).orElse("") ));
				
				System.out.println("==============================================================================");
			})
			.start();
	}

}
