package jpabook.start.relationship.compositeKey.nonIdentifying.embeddedId;

import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.relationship.compositeKey.nonIdentifying.embeddedId.entity.Parent;
import jpabook.start.relationship.compositeKey.nonIdentifying.embeddedId.entity.ParentId;

public class NonIdentifying_EmbeddedIdMain {

	public static void main(String[] args) {
		Print print = new Print();
		
		new Logic()
			.logic((em, tx) -> {
				print.mainStartPrint("[복합 키 - @EmbeddedId] 비식별 관계 매핑 저장");

				tx.begin();
				
				Parent parent = new Parent(new ParentId("id1", "id2"), "sinnake");
				em.persist(parent);
				
				tx.commit();
				
				print.mainEndPrint();
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("[복합 키 - @EmbeddedId] 비식별 관계 매핑 조회");
				
				Parent parent = em.find(Parent.class, new ParentId("id1", "id2"));
				
				System.out.println(String.format("Id1 : %s, Id2 : %s, name : %s"
					, Optional.ofNullable(parent.getId1()).orElse("")
					, Optional.ofNullable(parent.getId2()).orElse("")
					, Optional.ofNullable(parent.getName()).orElse("") ));

				print.mainEndPrint();				
			})
			.start();
		
	}

}
