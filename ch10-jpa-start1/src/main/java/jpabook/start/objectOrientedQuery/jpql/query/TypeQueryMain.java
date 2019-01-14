package jpabook.start.objectOrientedQuery.jpql.query;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.objectOrientedQuery.jpql.JpqlCommon;
import jpabook.start.objectOrientedQuery.jpql.entity.Member;

public class TypeQueryMain extends JpqlCommon {

	/*
	 * TypeQuery, Query
	 * 
	 * getResultList() : 결과를 컬렉션으로 반환한다. 만약 결과가 없으면 빈 컬렉션을 반환한다.
	 * getSingleResult() : 결과가 정확히 하나일 때 사용한다.
	 * 	- 결과가 없으면 javax.persistence.NoResultException 예외가 발생한다.
	 * 	- 결과가 1개보다 많으면 javax.persistence.NonUniqueResultException 예외가 발생한다.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		initSave();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				// 반환할 타입을 명확하게 지정할 수 있으면 TypeQuery을 사용.
				System.out.println("=============== TypeQuery 사용 ===============");
				TypedQuery<Member> typeQuery = em.createQuery("SELECT m FROM CH10_OOQ_MEMBER m", Member.class);
				List<Member> typeQuerymembers = typeQuery.getResultList();
				
				typeQuerymembers.stream().forEach(m -> {
					System.out.println("memberName : " + m.getUserName());
					System.out.println("memberTeam : " + m.getTeam().getName());
				});
				System.out.println("==============================================");
	
				
				// 반환할 타입이 명확치 않으면 Query을 사용. 
				System.out.println("=============== query 사용 ===============");
				Query query = em.createQuery("SELECT m FROM CH10_OOQ_MEMBER m");
				List queryMembers = query.getResultList();
				
				queryMembers.stream().forEach(m -> {
					System.out.println("memberName : " + ((Member)m).getUserName());
					System.out.println("memberAge : " + ((Member)m).getAge());
					System.out.println("memberTeam : " + ((Member)m).getTeam().getName());
				});
				System.out.println("==========================================");
			})
			.start();		
	}
}
