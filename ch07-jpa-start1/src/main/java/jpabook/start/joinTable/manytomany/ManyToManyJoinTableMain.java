package jpabook.start.joinTable.manytomany;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.joinTable.manytomany.entity.Child;
import jpabook.start.joinTable.manytomany.entity.Parent;

public class ManyToManyJoinTableMain {

	/*
	 * 다대다 조인 테이블
	 * 다대다 관계를 만들려면 조인 테이블의 두 컬럼을 합해서 하나의 복합 유니크 제약조건을 걸어야 한다.
	 * (PARENT_ID, CHILD_ID는 복합 기본 키이므로 유니크 제약조건이 걸려있다.)
	 */
	public static void main(String[] args) { 
		
		Print print = new Print();
		
		new Logic()
			.logic((em, tx) -> {
				print.mainStartPrint("다대다 조인테이블 저장");

				tx.begin();
				
				Parent parent = new Parent("sinnake_parent");
				
				Arrays.asList(
					new Child("sinnake_child1")
					, new Child("sinnake_child2")
					, new Child("sinnake_child3")
				).stream().forEach(c -> {
					em.persist(c);
					parent.addChild(c);
				});
	
				em.persist(parent);
				
				tx.commit();
				
				print.mainEndPrint();
			})
			.start();
	
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("다대다 조인테이블 저장");

				Parent parent = Optional.ofNullable(em.createQuery("select p from CH07_JOINTABLE_MANYTOMANY_PARENT p where p.id = :id", Parent.class)
					.setParameter("id", 1L)
					.getResultList() )
				.filter(p -> p.size() > 0)
				.map(p -> p.get(0))
				.orElseGet(Parent::new);
	
				List<Child> childs = Optional.ofNullable(parent.getChild()).orElseGet(ArrayList::new);
				childs.stream().forEach(c -> {					
					System.out.println(String.format("parent id : %s, parent name : %s, child id : %s, child name : %s"
						, Optional.ofNullable(parent.getId()).orElse(-1L)
						, Optional.ofNullable(parent.getName()).orElse("") 
						, Optional.ofNullable(c.getId()).orElse(-1L)
						, Optional.ofNullable(c.getName()).orElse("") ));	
				});
				
				print.mainEndPrint();
			})
			.start();
	}

}
