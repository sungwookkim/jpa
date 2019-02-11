package jpabook.start.objectOrientedQuery;

import java.util.Arrays;
import java.util.List;

import common.util.Logic;
import jpabook.start.objectOrientedQuery.entity.Member;
import jpabook.start.objectOrientedQuery.entity.Order;
import jpabook.start.objectOrientedQuery.entity.Product;
import jpabook.start.objectOrientedQuery.entity.Team;
import jpabook.start.objectOrientedQuery.entity.embedded.Address;

public class DataInit {

	public static void initSave() {
		new Logic()
		.logic((em, tx) -> {
			System.out.println("=============== JPQL 저장 ===============");
			tx.begin();
			
			/*
			 * 팀 객체
			 */
			Team uliTeam = new Team("우리반");
			Team namTeam = new Team("남반");
			
			/*
			 * 물품 객체
			 */
			List<Product> products = Arrays.asList(
				new Product("computer", 1000, 10)
				, new Product("notebook", 2000, 20)
				, new Product("smartPhone", 3000, 30)
				, new Product("keyboard", 4000, 40)
			);
			
			/*
			 * 회원 객체
			 */
			List<Member> uliMembers = Arrays.asList(
				new Member("sinnake1", 36)
				, new Member("sinnake2", 37)
			);

			List<Member> namMembers = Arrays.asList(
				new Member("sinnake4", 39)
				, new Member("sinnake5", 40)
			);
			
			/*
			 * 팀에 회원 객체 등록
			 */
			uliMembers.get(0).setTeam(namTeam);
			uliMembers.get(0).setTeam(uliTeam);
			uliMembers.stream().forEach(u -> {
				uliTeam.addMember(u);
			});
			
			namMembers.stream().forEach(n -> {
				namTeam.addMember(n);
			});
			
			
			/*
			 * 회원 구매 내역 등록
			 */
			uliMembers.stream().forEach(u -> {
				products.stream().forEach(p -> {
					// 주문
					Order order = new Order(new Address(u.getUserName() + "city", u.getUserName() + "street", "zipCode"), 1000);

					// 주문에 물품 저장
					order.setProduct(p);
					
					// 물품이 저장된 주문을 회원에 저장.
					u.addOrder(order);
				});
				
				em.persist(u);
			});		
			
			namMembers.stream().forEach(n -> {
				products.stream().forEach(p -> {
					Order order = new Order(new Address(n.getUserName() + "city", n.getUserName() + "street", "zipCode"), 1000);

					order.setProduct(p);
					n.addOrder(order);
				});
				
				em.persist(n);
			});

			tx.commit();
			System.out.println("=========================================");
		})
		.start();		
	}
}
