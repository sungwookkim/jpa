package jpabook.start.relationship.compositeKey.identifying.embeddedId;

import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.relationship.compositeKey.identifying.embeddedId.entity.Child;
import jpabook.start.relationship.compositeKey.identifying.embeddedId.entity.ChildId;
import jpabook.start.relationship.compositeKey.identifying.embeddedId.entity.GrandChild;
import jpabook.start.relationship.compositeKey.identifying.embeddedId.entity.GrandChildId;
import jpabook.start.relationship.compositeKey.identifying.embeddedId.entity.Parent;

public class Identifying_EmbeddedIdMain {
	/*
	 * @EmbeddedId
	 * 
	 * @EmbeddedId로 식별 관계를 구성할 때는 @MapsId를 사용해야 한다.
	 * @IdClass와 다른 점은 @Id 대신에 @MapsId를 사용한다.
	 * @MapsId의 속성 값은 @EmbeddedId를 사용한 식별자 클래스의 기본 키 필드를 지정해야 한다. 
	 */
	public static void main(String[] args) {
		ChildId childId = new ChildId(1L, "sinnake_child_id");
		GrandChildId grandChildId = new GrandChildId(childId, "sinnake_grandchild_id");
		
		new Logic()
			.logic((em, tx) -> {
				System.out.println("=============== [복합 키 - @EmbeddedId] 식별 관계 매핑 저장 ===============");
				tx.begin();
				
				Parent parent = new Parent("sinnake_parent_id", "sinnake_parent_name");
				Child child = new Child(parent, new ChildId(1L, "sinnake_child_id"), "sinnake_child_name");
				GrandChild grandChild = new GrandChild(grandChildId, child, "sinnake_grandChild_name");
				
				em.persist(parent);
				em.persist(child);
				em.persist(grandChild);
				
				tx.commit();
				System.out.println("===========================================================================");
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				System.out.println("=============== [복합 키 - @EmbeddedId] 식별 관계 Parent 조회 ===============");
				
				Parent parent = Optional.ofNullable(em.find(Parent.class, 1L)).orElseGet(Parent::new);

				System.out.println(String.format("parent Id : %s, parent MemberId : %s, parent MemberName : %s"
					, Optional.ofNullable(parent.getId()).map(String::valueOf).orElse("")
					, Optional.ofNullable(parent.getMemberId()).orElse("")
					, Optional.ofNullable(parent.getMemberName()).orElse("") ));
				
				System.out.println("=============================================================================");

				System.out.println("=============== [복합 키 - @EmbeddedId] 식별 관계 Child 조회 ===============");

				Child child = Optional.ofNullable(em.find(Child.class, childId)).orElseGet(Child::new);
				
				parent = Optional.ofNullable(child.getParent()).orElseGet(Parent::new);
						
				System.out.println(String.format("child Id : %s, child Name : %s, parent Id : %s, parent MemberId : %s, parent MemberName : %s"
					, Optional.ofNullable(child.getId()).map(ChildId::getId).orElse("")
					, Optional.ofNullable(child.getName()).orElse("")
					, Optional.ofNullable(parent.getId()).map(String::valueOf).orElse("")
					, Optional.ofNullable(parent.getMemberId()).orElse("")
					, Optional.ofNullable(parent.getMemberName()).orElse("") ));

				System.out.println("============================================================================");
				
				System.out.println("=============== [복합 키 - @EmbeddedId] 식별 관계 GrandChild 조회 ===============");

				GrandChild grandChild = Optional.ofNullable(em.find(GrandChild.class, grandChildId)).orElseGet(GrandChild::new);
				
				child = Optional.ofNullable(grandChild.getChildId()).orElseGet(Child::new);
				parent = Optional.ofNullable(child.getParent()).orElseGet(Parent::new);					
												
				System.out.println(String.format("grandChild Id : %s, grandChild name : %s, child Id : %s, child Name : %s, parent Id : %s, parent MemberId : %s, parent MemberName : %s"
					, Optional.ofNullable(grandChild.getGrandChildId()).map(GrandChildId::getId).orElse("")
					, Optional.ofNullable(grandChild.getName()).orElse("")
					, Optional.ofNullable(child.getId()).map(ChildId::getId).orElse("")
					, Optional.ofNullable(child.getName()).orElse("")
					, Optional.ofNullable(parent.getId()).map(String::valueOf).orElse("")
					, Optional.ofNullable(parent.getMemberId()).orElse("")
					, Optional.ofNullable(parent.getMemberName()).orElse("") ));
				
				System.out.println("=================================================================================");
			})
			.start();
	}
}
