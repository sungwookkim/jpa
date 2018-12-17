package jpabook.start;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import jpabook.start.entity.Member;

public class JpaMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			logic(em);
			tx.commit();
		} catch(Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		
		emf.close();
	}

	public static void logic(EntityManager em) {
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
		List<Member> members = em.createQuery("select m from Member m", Member.class)
			.getResultList();

		members.stream().forEach(m -> System.out.println("members = " + m));
		
		em.remove(member);
	}
}
