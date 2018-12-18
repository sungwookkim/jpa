package model1;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import common.util.Logic;
import model1.entity.Item;
import model1.entity.Member;
import model1.entity.Order;
import model1.entity.OrderItem;
import model1.entity.OrderStatus;

public class Model1Main {

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
		
		List<Order> orderList = Arrays.asList(
			new Order(new Long(1), new Date(), OrderStatus.ORDER)
			, new Order(new Long(2), new Date(-1), OrderStatus.ORDER)
			, new Order(new Long(2), new Date(), OrderStatus.CANCEL)
			, new Order(new Long(1), new Date(-2), OrderStatus.ORDER)
			, new Order(new Long(3), new Date(), OrderStatus.CANCEL)
			, new Order(new Long(3), new Date(-3), OrderStatus.ORDER)
			, new Order(new Long(1), new Date(), OrderStatus.ORDER)
		);
		
		List<OrderItem> orderItemList = Arrays.asList(
			new OrderItem(new Long(4), new Long(8), 20_000, 10)
			, new OrderItem(new Long(5), new Long(9), 30_000, 9)
			, new OrderItem(new Long(6), new Long(10), 40_000, 8)
			, new OrderItem(new Long(4), new Long(11), 50_000, 7)
			, new OrderItem(new Long(5), new Long(12), 60_000, 6)
			, new OrderItem(new Long(6), new Long(13), 70_000, 5)			
			, new OrderItem(new Long(7), new Long(14), 70_000, 5)
		);
		
		new Logic()
			.logic(em -> {
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
				
				orderList.stream()
					.forEach(order -> em.persist(order));
				
				orderItemList.stream()
					.forEach(orderItem -> em.persist(orderItem));
			})
			.commitAfter(em -> {				
				em.createQuery("select m from CH04_MODEL1_MEMBER m", Member.class)
					.getResultStream().forEach(m -> System.out.println("member = " + m));
				
				em.createQuery("select i from CH04_MODEL1_ITEM i", Item.class)
					.getResultStream().forEach(i -> System.out.println("item = " + i));
				
				em.createQuery("select o from CH04_MODEL1_ORDERS o", Order.class)
					.getResultStream().forEach(o -> System.out.println("order = " + o));				
				
				em.createQuery("select o from CH04_MODEL1_ORDER_ITEM o", OrderItem.class)
					.getResultStream().forEach(o -> System.out.println("order item = " + o));
				
				/*
				 * 주문번호 10번인 회원 정보 조회.
				 */
				System.out.println("\n주문번호 10번인 회원 정보 조회 : "
					+ em.find(Member.class, em.find(Order.class, new Long(10)).getMemberId()) );
			})
			.start();
	}

}
