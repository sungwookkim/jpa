package jpabook.start;

import jpabook.start.entity.Member;
import jpabook.start.util.Logic;

public class JpaMain {

	public static void main(String[] args) {
	
		new Logic().logic(em -> {
			String id = "id1";
			Member member = new Member();		
			member.setId(id);
			member.setUsername("지한");
			member.setAge(2);
			
			// 등록
			em.persist(member);

			// 한 건 조회
			Member findMember = em.find(Member.class, id);
			System.out.println("findMember = " + findMember);
			
			// 수정
			member.setAge(20);
			
			// 목록 조회
			em.createQuery("select m from Member m", Member.class)
				.getResultList()
				.stream().forEach(m -> System.out.println("members = " + m));
			
			em.remove(member);			
		});
	}
}
