package jpabook.start.cascade;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.cascade.entity.Child;
import jpabook.start.cascade.entity.Parent;

public class CascadeMain {

	/*
	 * 영속성 전이 : CASCADE
	 * 
	 * 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속 상태로 만들고 싶으면
	 * 영속성 전이(transitive persistence) 기능을 사용하면 된다. JPA는 CASCADE 옵션으로
	 * 영속성 전이를 제공한다. 쉽게 말해서 영속성 전이를 사용하면 부모 엔티티를 저장할 때
	 * 자식 엔티티도 함께 저장할 수 있다.
	 * 
	 * 영속성 전이는 연관관계를 매핑하는 것과는 아무 관련이 없다. 단지 "엔티티를 영속화할 때 연관된 엔티티도 같이 영속화하는 편리함을 제공"
	 * 할 뿐이다.
	 * 
	 * CASCADE의 종류
	 * 	ALL : 모두 적용
	 * 	PERSIST : 영속
	 * 	MERGE : 병합
	 * 	REMOVE : 삭제
	 * 	REFRESH : REFRESH 
	 * 	DETACH : DETACH
	 * 
	 * CascadeType.PERSIST, CascadeType.REMOVE는 em.persist(), em.remove()를 실행할 때 바로 전이가 발생하지 않고 플러시를 호출할 때
	 * 전이가 발생한다. 
	 */
	public static void main(String[] args) {
		new Logic()
			.logic((em, tx) ->{
				System.out.println("=============== 영속성 전이 저장 ===============");
				tx.begin();
				/*
				 * "JPA에서 엔티티를 저장할 때 연관된 모든 엔티티는 영속 상태여야 한다."
				 * 따라서 부모 엔티티를 영속 상태를 만들고 자식 엔티티도 각각 영속 상태로 만들어야 한다.
				 */
				/*
				Parent parent = new Parent("sinnake parent");
				Child child1 = new Child("sinnake child1");
				Child child2 = new Child("sinnake child2");
				
				child1.setParent(parent);
				child2.setParent(parent);
				
				em.persist(parent);
				em.persist(child1);
				em.persist(child2);
				*/
				
				/*
				 * 영속성 전이로 parent만 영속 시켜도 child1, child2에 까지 전이된다.
				 */
				Parent parent = new Parent("sinnake parent");
				Child child1 = new Child("sinnake child1");
				Child child2 = new Child("sinnake child2");
				
				parent.addChild(child1);
				parent.addChild(child2);
				
				em.persist(parent);
				tx.commit();
				System.out.println("================================================");
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.logic((em, tx) -> {
				System.out.println("=============== 영속성 전이 삭제 ===============");
				tx.begin();
				/*
				 * 영속성 전이 상태가 아닌 상황에서는 부모와 자식 테이블의 데이터를 삭제 하기 위해서는 아래와 같이 삭제 해야 한다.
				 */
				/*
				Parent parent = em.find(Parent.class, 1L);
				Child child1 = em.find(Child.class, 1L);
				Child child2 = em.find(Child.class, 2L);
				
				em.remove(child1);
				em.remove(child2);
				em.remove(parent);
				*/
				
				/*
				 * 영속성 전이 상태인 경우에는 연관관계가 맺어진 테이블의 데이터까지 같이 삭제한다.
				 */
				Parent parent = em.find(Parent.class, 1L);
				
				em.remove(parent);
				
				tx.commit();
				System.out.println("================================================");
			})
			.start();
		
	}

}
