package jpabook.start.access;

import common.util.Logic;
import jpabook.start.access.entity.AccessComplexMember;
import jpabook.start.access.entity.AccessFieldMember;
import jpabook.start.access.entity.AccessPropertyMember;

public class AccessMain {

	public static void main(String[] args) {
		
		/*
		 * AccessType.FIELD
		 */
		new Logic()
			.logic(em -> {
				AccessFieldMember accessFieldMember = new AccessFieldMember();
				accessFieldMember.setId("sinnake");
				accessFieldMember.setData1("sinnake data1");
				accessFieldMember.setData2("sinnake data2");
	
				AccessFieldMember accessFieldMember1 = new AccessFieldMember();
				accessFieldMember1.setId("sinnake1");
				accessFieldMember1.setData1("sinnake data11");
				accessFieldMember1.setData2("sinnake data21");
				
				em.persist(accessFieldMember);
				em.persist(accessFieldMember1);
			})
			.commitAfter(em -> {
				em.createQuery("select a from ACCESS_FIELD_MEMBER a", AccessFieldMember.class)
					.getResultStream().forEach(m -> System.out.println("access field member = " + m));				
			})
			.start();
		
		/*
		 * AccessType.PROPERTY
		 */
		new Logic()
			.logic(em -> {
				AccessPropertyMember accessPropertyMember = new AccessPropertyMember();			
				accessPropertyMember.setId("sinnake");
				accessPropertyMember.setData1("sinnake data1");
				accessPropertyMember.setData2("sinnake data2");
	
				AccessPropertyMember accessPropertyMember1 = new AccessPropertyMember();			
				accessPropertyMember1.setId("sinnake1");
				accessPropertyMember1.setData1("sinnake data11");
				accessPropertyMember1.setData2("sinnake data21");
	
				em.persist(accessPropertyMember);
				em.persist(accessPropertyMember1);				
			})
			.commitAfter(em -> {
				em.createQuery("select a from ACCESS_PROPERTY_MEMBER a", AccessPropertyMember.class)
					.getResultStream().forEach(m -> System.out.println("access property member = " + m));				
			})
			.start();
		
		/*
		 * AccessType.FIELD와 PROPERTY 복합 사용
		 */
		new Logic()
			.logic(em -> {
				AccessComplexMember accessComplexMember = new AccessComplexMember();
				accessComplexMember.setId("sinnake9");
				accessComplexMember.setFirstName("kim ");
				accessComplexMember.setLastName("sung wkim");
				
				AccessComplexMember accessComplexMember1 = new AccessComplexMember();
				accessComplexMember1.setId("sinnake10");
				accessComplexMember1.setFirstName("kim !");
				accessComplexMember1.setLastName("sung wkim !");
				
				em.persist(accessComplexMember);
				em.persist(accessComplexMember1);			
			})
			.commitAfter((em) -> {
				em.createQuery("select a from ACCESS_COMPLEX_MEMBER a", AccessComplexMember.class)
					.getResultStream().forEach(m -> System.out.println("access complex member = " + m));				
			})
			.start();
		
	}
}
