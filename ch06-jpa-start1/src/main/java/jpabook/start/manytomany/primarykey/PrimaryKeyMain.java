package jpabook.start.manytomany.primarykey;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.util.JPA_AUTO;
import common.util.Logic;
import jpabook.start.manytomany.primarykey.entity.Member;
import jpabook.start.manytomany.primarykey.entity.Orders;
import jpabook.start.manytomany.primarykey.entity.Product;

public class PrimaryKeyMain {

	/*
	 * 다대다 : 새로운 기본 키 사용
	 * 
	 * 연결 엔티티를 활용해도 되나 연결 엔티티를 활용하게 되면 별도의 식별자 클래스를 생성하고 관리해야 하는 불편사항이 있다.
	 * 그래서 추천하는 전략은 기본 키 생성 전략이다.
	 * 데이터베이스에서 자동으로 생성해주는 대리 키를 Long 값으로 사용하는 것이다.
	 * 장점은 간편하거 거의 영구히 쓸 수 있으며 비즈니스에 의존하지 않는다. 그리고 ORM 매핑 시에 복합 키를 만들지 않아도 되므로
	 * 간단히 매핑을 완성할 수 있다.
	 */
	public static void main(String[] args) {

		new Logic()
			.logic((em, tx) -> {
				System.out.println("=============== 회원, 상품 저장 ===============");
				tx.begin();

				Product product = new Product("product");
				Product product1 = new Product("product1");
				Product product2 = new Product("product2");
				
				Member member = new Member("sinnake", "sinnake_name");
				Member member1 = new Member("sinnake1", "sinnake1_name");
				
				Orders orders = new Orders(10_000_000);
				Orders orders1 = new Orders(20_000_000);
				Orders orders2 = new Orders(30_000_000);
				Orders orders3 = new Orders(40_000_000);
				Orders orders4 = new Orders(50_000_000);
				
				orders.setProduct(product);
				orders.setMember(member);
				
				orders1.setProduct(product1);
				orders1.setMember(member);				


				orders2.setProduct(product);				
				member1.addOrders(orders2);
				
				orders3.setProduct(product1);
				member1.addOrders(orders3);
				
				orders4.setProduct(product2);
				member1.addOrders(orders4);

				em.persist(member);
				em.persist(member1);
				
				em.persist(product);
				em.persist(product1);
				em.persist(product2);
				
				em.persist(orders);
				em.persist(orders1);
				em.persist(orders2);
				em.persist(orders3);
				em.persist(orders4);

				tx.commit();
				System.out.println("===============================================");
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				System.out.println("=============== sinnake1 회원의 product1 상품 구매내역 조회 ===============");
				
				Orders orders = Optional.ofNullable(em.find(Orders.class, new Long(4))).orElseGet(Orders::new);
				Member member = Optional.ofNullable(orders.getMember()).orElseGet(Member::new);
				Product product = Optional.ofNullable(orders.getProduct()).orElseGet(Product::new);
				
				System.out.println(String.format("%s 회원이 구매한 %s 상품의 가격 : %s"
						, Optional.ofNullable(member.getUserName()).orElse("")
						, Optional.ofNullable(product.getName()).orElse("")
						, Optional.ofNullable(orders.getOrderAmount()).map(String::valueOf).orElse("") ));
				
				System.out.println("===========================================================================");

				System.out.println("=============== product1 상품을 구매한 모든 회원 조회 ===============");

				List<Orders> ordersList = Optional.ofNullable(em.createQuery("select p from CH06_MANY_TO_MANY_PRIMARYKEY_PRODUCT p where p.name = :name", Product.class)
					.setParameter("name", "product1").getResultList())
				.filter(p -> p.size() > 0)
				.map(p -> p.get(0))
				.map(p -> {
					return Optional.ofNullable(em.createQuery("select o from CH06_MANY_TO_MANY_PRIMARYKEY_ORDERS o where o.product.id = :id", Orders.class)
						.setParameter("id", p.getId()).getResultList() )
					.filter(o -> o.size() > 0)
					.orElseGet(ArrayList::new);
				})
				.orElseGet(ArrayList::new);
				
				ordersList.stream().forEach(o -> {
					System.out.println(String.format("product1 상품을 구매한 모든 회원 ID : %s, 구매한 금액 : %s"
						, Optional.ofNullable(o.getMember()).map(Member::getUserId).orElse("") 
						, Optional.ofNullable(o.getOrderAmount()).map(String::valueOf).orElse("") ));
				});

				System.out.println("=====================================================================");
			})
			.start();
		
	}

}
