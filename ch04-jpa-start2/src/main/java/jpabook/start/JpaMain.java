package jpabook.start;

import java.util.Date;

import common.util.Logic;
import jpabook.start.entity.Member;
import jpabook.start.entity.RoleType;

public class JpaMain {

	public static void main(String args[]) {

		new Logic()
			.logic(em -> {
				Member member1 = new Member();
				member1.setId("sinnake");
				member1.setUsername("신나게");
				member1.setAge(35);
				member1.setCreatedDate(new Date());
				member1.setLastModifiedDate(new Date());
				member1.setDescription("신나게 입니다.");
				member1.setRoleType(RoleType.ADMIN);
	
				Member member2 = new Member();
				member2.setId("sinnake2");
				member2.setUsername("신나게2");
				member2.setAge(36);
				member2.setCreatedDate(new Date());
				member2.setLastModifiedDate(new Date());
				member2.setDescription("신나게 입니다.2");
				member2.setRoleType(RoleType.USER);
				
				em.persist(member1);
				em.persist(member2);
				
			})
			.commitAfter(em -> {
				// 목록 조회
				em.createQuery("select m from Member m", Member.class)
					.getResultStream().forEach(m -> System.out.println("members = " + m));				
			})
			.start();
	}
}
