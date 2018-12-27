package jpabook.start.manytomany.connectEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.manytomany.connectEntity.entity.Member;
import jpabook.start.manytomany.connectEntity.entity.MemberProduct;
import jpabook.start.manytomany.connectEntity.entity.MemberProductId;
import jpabook.start.manytomany.connectEntity.entity.Product;

public class ManyToMany_ConnectEntityMain {

	/*
	 * 다대다 : 매핑의 한계와 극복, 연결 엔티티 사용.
	 * 
	 * @ManyToMany를 사용하면 연결 테이블을 자동으로 처리해주므로 도메인 모델이 단순해지고 여러 가지 편리하다.
	 * 하지만 이 매핑을 실무에서 사용하기에는 한계가 있다.
	 * @JoinTable 어노테이션으로 생성된 연결 테이블은 외래 키 외 다른 컬럼을 조회 할 수 없기 때문이다.
	 * 연결 테이블에서 외래 키 외 다른 컬럼을 조회 해야 하는 경우에는 연결 엔티티를 활용하면 된다. 
	 * 
	 * 복합 기본키 
	 * JPA에서 복합 키를 사용하려면 별도의 식별자 클래스를 만들어야 한다.
	 * 그리고 엔티티에 @IdClass를 사용해서 식별자 클래스를 지정하면 된다.
	 * 
	 * 복합 기본키를 위한 식별자 클래스는 다음과 같은 특징이 있다.
	 * - 복합 키는 별도의 식별자 클래스로 만들어야 한다.
	 * - Serializable을 구현해야 한다.
	 * - equals와 hashCode 메서드를 구현해야 한다.
	 * - 기본 생성자가 있어야 한다.
	 * - 식별자 클래스는 public이어야 한다.
	 * - @IdClass를 사용하는 방법 외에는 @EmbeddebId를 사용하는 방법도 있다.
	 * 
	 * 식별관계
	 * 기본 키들을 받아서 자신의 기본 키로 사용한다. 이렇게 부모 테이블의 기본 키를 받아서 자신의 기본 키 + 외래 키로
	 * 사용하는 것을 데이터베이스 용어로 식별 관계(Identifying Pelationship)라 한다.
	 */
	public static void main(String[] args) {
		
		new Logic()
			.logic((em, tx) -> {
				System.out.println("=============== 회원, 상품 저장 ===============");				
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
				member1.addMemberProduct(memberProduct1);
				
				memberProduct2.setProduct(product2);
				member1.addMemberProduct(memberProduct2);
				
				memberProduct3.setProduct(product3);
				member1.addMemberProduct(memberProduct3);
				
				memberProduct4.setProduct(product4);
				member1.addMemberProduct(memberProduct4);


				memberProduct11.setProduct(product1);
				member2.addMemberProduct(memberProduct11);
				
				memberProduct12.setProduct(product2);
				member2.addMemberProduct(memberProduct12);
				
				memberProduct13.setProduct(product3);
				member2.addMemberProduct(memberProduct13);
				
				memberProduct14.setProduct(product4);
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
				System.out.println("===============================================");
			})
			.start();
			
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				System.out.println("=============== sinnake1 회원이 구매한 모든 상품 조회 ===============");				
				
				Member member = Optional.ofNullable(em.createQuery("select m from CH06_MANY_TO_MANY_CONNECT_MEMBER m where m.userId = :userId", Member.class)
					.setParameter("userId", "sinnake1")
					.getResultList())
				.filter(m -> m.size() > 0)
				.map(m -> m.get(0))
				.orElse(new Member() );

				List<MemberProduct> memberProducts = Optional.ofNullable(member.getMemberProduct())
					.orElseGet(ArrayList::new);
				
				memberProducts.stream().forEach(mp -> {
					System.out.println(String.format("회원 ID : %s, 상품 이름 : %s, 가격 : %s" 
						, Optional.ofNullable(mp.getMember()).map(Member::getUserId).orElse("")
						, Optional.ofNullable(mp.getProduct()).map(Product::getName).orElse("")
						, Optional.ofNullable(mp.getOrderAmount()).map(String::valueOf).orElse("") ));
				});
				
				System.out.println("====================================================================");
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				System.out.println("=============== sinnake2 회원의 product1 상품 구매내역 조회 ===============");
				
				MemberProduct memberProduct = Optional.ofNullable(em.find(MemberProduct.class, new MemberProductId(3, 2)))
						.orElseGet(MemberProduct::new);
				Member member = Optional.ofNullable(memberProduct.getMember()).orElseGet(Member::new);
				Product product = Optional.ofNullable(memberProduct.getProduct()).orElseGet(Product::new);
				
				System.out.println(String.format("%s 회원이 구매한 %s 상품의 가격 : %s"
					, Optional.ofNullable(member.getUserName()).orElse("")
					, Optional.ofNullable(product.getName()).orElse("")
					, Optional.ofNullable(memberProduct.getOrderAmount()).map(String::valueOf).orElse("") ));
				
				System.out.println("===========================================================================");
			})
			.start();
	}

}
