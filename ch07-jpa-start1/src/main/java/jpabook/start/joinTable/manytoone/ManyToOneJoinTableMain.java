package jpabook.start.joinTable.manytoone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.joinTable.manytoone.entity.Child;
import jpabook.start.joinTable.manytoone.entity.Parent;

public class ManyToOneJoinTableMain {
	/*
	 * 다대일 조인 테이블
	 * 다대일은 일대다에서 방향만 반대이므로 조인 테이블 모양은 일대다에서 설명한 것과 같다.
	 */
	public static void main(String[] args) {
		
		Print print = new Print();
		
		new Logic()
			.logic((em, tx) -> {
				print.mainStartPrint("다대일 조인테이블 저장");

				tx.begin();
				
				Parent parent = new Parent("sinnake_parent");
				em.persist(parent);
				
				Arrays.asList(
					new Child(parent, "sinnake_child1")
					, new Child(parent, "sinnake_child1")
					, new Child(parent, "sinnake_child1")
				).stream().forEach(c -> {
					em.persist(c);
				});

				tx.commit();
				
				print.mainEndPrint();
			})
			.start();

		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("다대일 조인테이블 조회");

				Parent parent = Optional.ofNullable(em.createQuery("select p from CH07_JOINTABLE_MANYTOONE_PARENT p where p.id = :id", Parent.class)
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
