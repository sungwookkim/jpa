package jpabook.start.orphan;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.orphan.entity.Child;
import jpabook.start.orphan.entity.Parent;

public class OrphanMain {

	/*
	 * 고아 객체
	 * 
	 * JPA는 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제하는 기능을 제공하는데
	 * 이것을 고아 객체(ORPHAN)제거라 한다. 이 기능을 사용하면 
	 * "부모 엔티티의 컬렉션에서 자식 엔티티의 참조만 제거하면 자식 엔티티가 자동으로 삭제
	 * "된다.
	 * 
	 * 고아 객체 제거는 "참조가 제거된 엔티티는 다른 곳에서 참조하지 않는 고아 객체로 보고 삭제하는 기능"이다.
	 * 따라서 이 기능은 참조하는 곳이 하나일 때만 사용해야 한다.
	 * 특정 엔티티가 개인 소유하는 엔티티에만 이 기능을 적용해야 한다. 
	 * 이런 이유로 orphanRemoval은 @OneToOne, @OneToMany에만 사용할 수 있다.
	 * 
	 * 고아 객체 제거에는 기능이 하나 더 있는데 개념적으로 볼때 부모를 제거하면 자식은 고아가 된다.
	 * 따라서 부모를 제거하면 자식도 같이 제거된다. 이것은 CascadeType.REMOVE를 설정한 것과 같다.
	 */
	public static void main(String[] args) {

		Print print = new Print();
		
		new Logic()
			.logic((em, tx) ->{
				print.mainStartPrint("고아 객체 저장");

				tx.begin();

				Parent parent = new Parent("sinnake parent");
				Child child1 = new Child("sinnake child1");
				Child child2 = new Child("sinnake child2");
				
				parent.addChild(child1);
				parent.addChild(child2);
				
				em.persist(parent);
				tx.commit();
				
				print.mainEndPrint();
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.logic((em, tx) -> {
				print.mainStartPrint("고아 객체 삭제");

				tx.begin();

				Parent parent = Optional.ofNullable(em.find(Parent.class, 1L)).orElseGet(Parent::new);
				
				List<Child> childs = Optional.ofNullable(parent.getChild()).orElseGet(ArrayList::new);
				/*
				 * 자식 엔티티를 컬렉션에서 제거하면 자식 테이블의 해당 데이터도 같이 삭제된다.
				 */
				childs.remove(0);
				
				/*
				 * 아래와 같이 clear 메서드를 호출해서 컬렉션을 비우게 되면 자식 테이블의 
				 * 해당되는 모든 데이터가 삭제된다.
				 */
				//childs.clear();
				
				tx.commit();
				
				print.mainEndPrint();
			})
			.start();
	}

}
