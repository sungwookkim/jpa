package model2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.valueString.impl.JSON;
import model2.entity.Item;
import model2.entity.Member;
import model2.entity.Order;
import model2.entity.OrderItem;
import model2.entity.OrderStatus;

public class Model2Main {

	public static void main(String[] args) {
		List<Member> memberList = Arrays.asList(
				new Member("신나게", "서울", "길거리", "000-000")
				, new Member("쉰나게", "지방", "골목길", "100-100")
				, new Member("쒼나게", "우주", "화성언저리", "200-200")
			);
			
		List<Item> itemList = Arrays.asList(
			new Item("컴퓨터", 400_000_000, 10)
			, new Item("태블릿", 300_000_000, 9)
			, new Item("게임기", 200_000_000, 8)
			, new Item("스위치", 100_000_000, 7)
		);
		
		new Logic()
			.logic((em, tx) -> {
				System.out.println("=============== 회원, 물품, 주문 저장 ===============");
				tx.begin();

				/*
				 * 회원 저장
				 */
				memberList.stream()
					.forEach(member -> em.persist(member));
				
				/*
				 * 물품 저장
				 */
				itemList.stream()
					.forEach(item -> em.persist(item));
				
				/*
				 * 주문 저장
				 */
				List<Order> orderList = Arrays.asList(
					new Order(new Date(), OrderStatus.ORDER, memberList.get(0))
					, new Order(new Date(), OrderStatus.CANCEL, memberList.get(0))
					, new Order(new Date(), OrderStatus.ORDER, memberList.get(0))
					, new Order(new Date(), OrderStatus.CANCEL, memberList.get(1))
					, new Order(new Date(), OrderStatus.CANCEL, memberList.get(1))
					, new Order(new Date(), OrderStatus.ORDER, memberList.get(1))
					, new Order(new Date(), OrderStatus.ORDER, memberList.get(2))					
					, new Order(new Date(), OrderStatus.CANCEL, memberList.get(2))
					, new Order(new Date(), OrderStatus.ORDER, memberList.get(2))
				);

				/*
				 * 주문물품 저장.
				 */
				orderList.stream()
					.forEach(order -> {
						em.persist(order);

						List<OrderItem> orderItemList = Arrays.asList(
							new OrderItem(itemList.get(0), order, 2000, 1)
							, new OrderItem(itemList.get(1), order, 3000, 2)
							, new OrderItem(itemList.get(2), order, 4000, 3)
							, new OrderItem(itemList.get(3), order, 5000, 4)
						);
						
						orderItemList.forEach(orderItem -> em.persist(orderItem) );
					});

				tx.commit();
				System.out.println("=====================================================");
			}).start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				System.out.println("=============== 주문번호가 5인 회원의 모든 주문내역 확인 ===============");				
				Order order = em.find(Order.class, new Long(5));
				order.getMember().getOrder().stream().forEach(o -> {
					System.out.println("이름 : " + o.getMember().getName() + ", 주문번호 : " + o.getOrderId());

					System.out.println("--------------- 주문 물품 ---------------");
					o.getOrderItem().forEach(orderItem -> {
						System.out.println("갯수 : " + orderItem.getCount());
						System.out.println("가격 : " + orderItem.getOrderPrice());
						System.out.println(JSON.valueString(orderItem.getItem()) );
					});
					System.out.println("-----------------------------------------");
				});
				System.out.println("========================================================================");
				
				System.out.println("=============== 컴퓨터를 주문한 모든 회원 ===============");
				List<Member> members = Optional.ofNullable(em.createQuery("select i from CH05_MODEL2_ITEM i where i.name = '컴퓨터'", Item.class)
						.getResultList())
					.filter(items -> items.size() > 0)
					.map(items -> items.get(0))
					.map(item -> Optional.ofNullable(em.createQuery("select o.order.member from CH05_MODEL2_ORDER_ITEM o where o.item.id = :itemID", Member.class)
							.setParameter("itemID", item.getId())
							.getResultList() )
						.filter(m -> m.size() > 0)					
						.orElse(new ArrayList<>()) )
					.orElse(new ArrayList<>());
				
				members.stream()
					.distinct()
					.collect(Collectors.toList())
					.forEach(m -> System.out.println(m.getName()));
				System.out.println("=========================================================");
				
				System.out.println("=============== 신나게 회원의 구매한 모든 물품 ===============");
				em.find(Member.class, new Long(1))
					.getOrder().stream().forEach(o -> {
						System.out.println("주문자 : " + o.getMember().getName() + ", 주문번호 : " + o.getOrderId());
						System.out.println("주문물품 : ");
						o.getOrderItem().stream().forEach(oi -> {
							System.out.println("주문물품번호 : " + oi.getId() 
								+ ", 주문수량 : " + oi.getCount()
								+ ", 주문금액 : " + oi.getOrderPrice()
								+ ", 물품명 : " + oi.getItem().getName());
						});
					});
				System.out.println("==============================================================");

			})
			.start();
	}

}
