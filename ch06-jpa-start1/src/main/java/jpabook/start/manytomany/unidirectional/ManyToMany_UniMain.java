package jpabook.start.manytomany.unidirectional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.manytomany.unidirectional.entity.Member;
import jpabook.start.manytomany.unidirectional.entity.Product;

public class ManyToMany_UniMain {

	public static void main(String[] args) {
		List<Product> productList = Arrays.asList(
			new Product("product1")
			, new Product("product2")
			, new Product("product3")
		);
		
		new Logic()
			.logic((em, tx) -> {
				System.out.println("=============== 회원, 상품 저장 ===============");
				tx.begin();
				
				Member member = new Member("sinnake", "sinnake_name");				
				
				productList.stream().forEach(p -> {
					member.addProduct(p);
					em.persist(p);
				});				
								
				em.persist(member);
				
				tx.commit();
				System.out.println("===============================================");
			})
			.start();
		
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				System.out.println("=============== sinnake 회원의 상품 조회 ===============");

				Member member = Optional.ofNullable(em.createQuery("select m from CH06_MANY_TO_MANY_UNI_MEMBER m where m.userId = 'sinnake'", Member.class)
					.getResultList())
				.filter(m -> m.size() > 0)
				.map(m -> m.get(0))
				.orElse(new Member());
				
				List<Product> products =  Optional.ofNullable(member.getProduct()).orElse(new ArrayList<>());
				products.stream().forEach(p -> {
					System.out.println(String.format("sinnake 회원의 상품 : %s"
						, Optional.ofNullable(p.getName()).orElse("") ));
				});
				
				System.out.println("========================================================");
			})
			.start();
	}
}
