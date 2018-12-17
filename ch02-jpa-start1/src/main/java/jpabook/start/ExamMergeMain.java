package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.start.entity.Member;

public class ExamMergeMain {
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

	static Member createMember(String id, String username, int age) {
		// 영속성 컨텍스트1 시작
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		Member member = new Member();
		member.setId(id);
		member.setUsername(username);
		member.setAge(age);

		em.persist(member);
		tx.commit();
		
		// 영속성 컨텍스트 종료(member 객체는 준영속 상태가 된다.)
		em.close();
		
		return member;
	}
	
	static void mergeMember(Member member) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		Member mergeMember = em.merge(member);
		tx.commit();
		
		// 준영속 상태
		System.out.println("member = " + member.getUsername());
		
		// 영속 상태
		System.out.println("mergerMember = " + mergeMember.getUsername());
		
		System.out.println("em contains member = " + em.contains(member));
		System.out.println("em contains mergerMember = " + em.contains(mergeMember));
		
		tx.begin();
		em.remove(mergeMember);
		tx.commit();

		em.close();
	}
	
	public static void main(String[] args) {
		Member member = createMember("sinnake", "신나게", 35);
		
		member.setUsername("신나게2");
		
		mergeMember(member);
	}


}
