package model3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import model3.entity.Category;
import model3.entity.CategoryItem;
import model3.entity.Delivery;
import model3.entity.DeliveryStatus;
import model3.entity.Item;
import model3.entity.Member;
import model3.entity.Order;
import model3.entity.OrderItem;
import model3.entity.OrderStatus;

public class Model3Main {

	public static void main(String[] args) {
		
		Print print = new Print();
		
		new Logic()
			.logic((em, tx) -> {
				print.mainStartPrint("카테고리, 상품, 회원, 주문 저장");

				tx.begin();
				
				/************/
				/* 카테고리 */
				/************/
				Category categoryComputer = new Category("컴퓨터");
				Category categoryFood = new Category("식품");
				
				/**************/
				/* 컴퓨터 상품*/
				/**************/
				List<Item> computerItems = Arrays.asList(
					new Item("조립식", 100_000_000, 100)
					, new Item("완제품", 300_000_000, 100)
					, new Item("그래픽카드", 300_000_000, 100)
				);
				
				/************/
				/* 식품 상품*/
				/************/
				List<Item> foodItems = Arrays.asList(
					new Item("딸기", 100_000_000, 100)
					, new Item("포도", 300_000_000, 100)
					, new Item("참외", 300_000_000, 100)
				);
				
				BiFunction<Item, Category, CategoryItem> setCategoryItem = (i, c) -> {
					CategoryItem categoryItem = new CategoryItem();					
					categoryItem.setItem(i);
					categoryItem.setCategory(c);

					em.persist(i);
					em.persist(categoryItem);

					return categoryItem;
				};
				
				/***********************/
				/* 상품, 카테고리 저장 */
				/***********************/
				em.persist(categoryComputer);
				em.persist(categoryFood);
				
				computerItems.stream()
					.map(ci -> setCategoryItem.apply(ci, categoryComputer))
					.collect(Collectors.toList());
				
				foodItems.stream()
					.map(fi -> setCategoryItem.apply(fi, categoryFood))
					.collect(Collectors.toList());
				
				/*************/
				/* 주문 저장 */
				/*************/
				Member member1 = new Member("신나게1", "sinnake1", "서울1", "7번가1", "123456");
				Delivery delivery1 = new Delivery(member1.getCity(), member1.getStreet(), member1.getZipcode(), DeliveryStatus.READY);
				Order order1 = new Order(new Date(), OrderStatus.ORDER, member1, delivery1);
				
				Member member2 = new Member("신나게2", "sinnake2", "서울2", "7번가2", "789456");
				Delivery delivery2 = new Delivery(member2.getCity(), member2.getStreet(), member2.getZipcode(), DeliveryStatus.READY);
				Order order2 = new Order(new Date(), OrderStatus.ORDER, member2, delivery2);
				
				BiFunction<Item, Order, OrderItem> setOrderItem = (i, o) -> {
					OrderItem orderItem = new OrderItem(i, o, i.getPrice(), i.getStockquantity() - 1);

					em.persist(orderItem);

					return orderItem;
				};
				
				em.persist(member1);
				em.persist(delivery1);
				em.persist(order1);
				
				em.persist(member2);
				em.persist(delivery2);
				em.persist(order2);
				
				// sinnake1 회원 컴퓨터 상품 구매.
				computerItems.stream()
					.map(ci -> setOrderItem.apply(ci, order1))
					.collect(Collectors.toList());

				// sinnake1 회원 식품 상품 구매.
				foodItems.stream()
					.map(ci -> setOrderItem.apply(ci, order1))
					.collect(Collectors.toList());

				// sinnake2 회원 컴퓨터 상품 구매.
				computerItems.stream()
					.map(ci -> setOrderItem.apply(ci, order2))
					.collect(Collectors.toList());

				// sinnake2 회원 식품 상품 구매.
				foodItems.stream()
					.map(ci -> setOrderItem.apply(ci, order2))
					.collect(Collectors.toList());
				
				tx.commit();
				
				print.mainEndPrint();
			})
			.start();
			
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("sinnake1 회원이 구매한 전체 상품 내역");
				
				List<Order> order = Optional.ofNullable(em.createQuery("select o from CH06_MODEL3_ORDERS o where o.member.userId = :userId", Order.class)									
					.setParameter("userId", "sinnake1")
					.getResultList())
				.filter(o -> o.size() > 0)
				.orElseGet(ArrayList::new);

				Optional.ofNullable(order)
					.filter(o -> o.size() > 0)
					.ifPresent(orders -> {
						
						orders.stream().forEach(o -> {
							Optional.ofNullable(o.getDelivery())
								.ifPresent(delivery -> {
									System.out.println(String.format("[배송 정보] 도시 : %s, 거리 : %s, 우편번호 : %s, 상태 : %s" 
										, Optional.ofNullable(delivery.getCity()).orElse("")
										, Optional.ofNullable(delivery.getStreet()).orElse("")									
										, Optional.ofNullable(delivery.getZipCode()).orElse("")
										, Optional.ofNullable(delivery.getDeliveryStatus()).map(String::valueOf).orElse("") ));
								});

							Optional.ofNullable(o.getOrderItem())
								.filter(orderItem -> orderItem.size() > 0)
								.orElseGet(ArrayList::new)
								.stream()
								.forEach(orderItem -> {
									System.out.println(String.format("상품명 : %s, 가격 : %s, 주문날짜 : %s"
										, Optional.ofNullable(orderItem.getItem()).map(Item::getName).orElse("")
										, Optional.ofNullable(orderItem.getOrderPrice()).map(String::valueOf).orElse("") 
										, Optional.ofNullable(o.getOrderDate()).map(String::valueOf).orElse("") ));									
								});
						});
					});
				
				print.mainEndPrint();
			})
			.start();
	}

}
