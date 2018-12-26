package jpabook.start.manytomany.connectEntity;

import common.util.Logic;
import jpabook.start.manytomany.connectEntity.entity.Member;
import jpabook.start.manytomany.connectEntity.entity.MemberProduct;
import jpabook.start.manytomany.connectEntity.entity.Product;

public class ManyToMany_ConnectEntityMain {

	public static void main(String[] args) {
		new Logic()
			.logic((em, tx) -> {
				tx.begin();
								
				Product product = new Product("product");
				Product product1 = new Product("product1");
				Product product2 = new Product("product2");
				Product product3 = new Product("product3");
				Product product4 = new Product("product4");			
				
				Member member = new Member("sinnake", "sinnake_name");
				Member member1 = new Member("sinnake1", "sinnake1_name");
				Member member2 = new Member("sinnake2", "sinnake2_name");
								
				MemberProduct memberProduct = new MemberProduct(10_000_000);
				MemberProduct memberProduct1 = new MemberProduct(10_000_000);
				MemberProduct memberProduct2 = new MemberProduct(20_000_000);
				MemberProduct memberProduct3 = new MemberProduct(30_000_000);
				MemberProduct memberProduct4 = new MemberProduct(40_000_000);

				MemberProduct memberProduct11 = new MemberProduct(10_000_000);
				MemberProduct memberProduct12 = new MemberProduct(20_000_000);
				MemberProduct memberProduct13 = new MemberProduct(30_000_000);
				MemberProduct memberProduct14 = new MemberProduct(40_000_000);				
				
				memberProduct.setMember(member);
				memberProduct.setProduct(product);				

				memberProduct1.setProduct(product1);
				memberProduct2.setProduct(product2);
				memberProduct3.setProduct(product3);
				memberProduct4.setProduct(product4);
				
				member1.addMemberProduct(memberProduct1);
				member1.addMemberProduct(memberProduct2);
				member1.addMemberProduct(memberProduct3);
				member1.addMemberProduct(memberProduct4);
				
				memberProduct11.setProduct(product1);
				memberProduct12.setProduct(product2);
				memberProduct13.setProduct(product3);
				memberProduct14.setProduct(product4);
				
				member2.addMemberProduct(memberProduct11);
				member2.addMemberProduct(memberProduct12);
				member2.addMemberProduct(memberProduct13);
				member2.addMemberProduct(memberProduct14);
				
				em.persist(member);
				em.persist(member1);
				em.persist(member2);
				em.persist(product);
				em.persist(product1);
				em.persist(product2);
				em.persist(product3);
				em.persist(product4);
				em.persist(memberProduct);
				em.persist(memberProduct1);
				em.persist(memberProduct2);
				em.persist(memberProduct3);
				em.persist(memberProduct4);
				em.persist(memberProduct11);
				em.persist(memberProduct12);
				em.persist(memberProduct13);
				em.persist(memberProduct14);

				tx.commit();
			})
			.start();
			
		
	}

}
