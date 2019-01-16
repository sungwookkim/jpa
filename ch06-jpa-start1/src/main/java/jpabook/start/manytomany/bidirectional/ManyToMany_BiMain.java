package jpabook.start.manytomany.bidirectional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.manytomany.bidirectional.entity.Member;
import jpabook.start.manytomany.bidirectional.entity.Product;

public class ManyToMany_BiMain {

	public static void main(String[] args) {
		
		Print print = new Print();
		
		new Logic()
			.logic((em, tx) -> {
				print.mainStartPrint("회원, 상품 저장");

				tx.begin();
				
				Member member = new Member("sinnake", "sinnake_name");
				Member member1 = new Member("sinnake1", "sinnake1_name");
				Member member2 = new Member("sinnake2", "sinnake2_name");
				Member member3 = new Member("sinnake3", "sinnake3_name");
				Member member4 = new Member("sinnake4", "sinnake4_name");
				
				Product product = new Product("product");
				Product product1 = new Product("product1");
				Product product2 = new Product("product2");
				Product product3 = new Product("product3");
				
				member.addProduct(product);
				member.addProduct(product1);
				member.addProduct(product2);
				member.addProduct(product3);
				
				member1.addProduct(product2);
				member2.addProduct(product3);
				member3.addProduct(product1);
				
				product1.addMember(member1);
				product1.addMember(member2);
				product1.addMember(member3);

				product2.addMember(member2);
				product2.addMember(member3);
				product2.addMember(member4);
				
				em.persist(member);
				em.persist(member1);
				em.persist(member2);
				em.persist(member3);
				em.persist(member4);

				em.persist(product);
				em.persist(product1);
				em.persist(product2);
				em.persist(product3);

				tx.commit();
				
				print.mainEndPrint();
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("sinnake1 회원이 구매한 모든 상품");
				
				Member member = Optional.ofNullable(em.createQuery("select m from CH06_MANY_TO_MANY_BI_MEMBER m where m.userId = :userId", Member.class)
					.setParameter("userId", "sinnake1").getResultList() )
				.filter(m -> m.size() > 0)
				.map(m -> m.get(0))
				.orElseGet(Member::new);

				List<Product> products = Optional.ofNullable(member.getProduct())
					.filter(p -> p.size() > 0)
					.orElseGet(ArrayList::new);
				
				products.stream().forEach(p -> {
					System.out.println(String.format("%s 회원이 구매한 상품 : %s"
						, Optional.ofNullable(member.getUserId()).orElse("")
						, Optional.ofNullable(p.getName()).orElse("") ));
				});
				
				print.mainEndPrint();
				
				
				
				print.mainStartPrint("product1 상품을 구매한 모든 회원");

				Product product = Optional.ofNullable(em.createQuery("select p from CH06_MANY_TO_MANY_BI_PRODUCT p where p.name = :name", Product.class)
					.setParameter("name", "product1").getResultList() )
				.filter(p -> p.size() > 0)
				.map(p -> p.get(0))
				.orElseGet(Product::new);
				
				List<Member> members = Optional.ofNullable(product.getMember())
					.filter(m -> m.size() > 0)
					.orElseGet(ArrayList::new);
				
				members.stream().forEach(m -> {
					System.out.println(String.format("%s 상품을 구매한 회원 ID : %s"
						, Optional.ofNullable(product.getName()).orElse("") 
						, Optional.ofNullable(m.getUserId()).orElse("") ));
				});

				print.mainEndPrint();
			})
			.start();
	}
	
}
