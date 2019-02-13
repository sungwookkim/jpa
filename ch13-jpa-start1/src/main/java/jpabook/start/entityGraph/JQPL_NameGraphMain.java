package jpabook.start.entityGraph;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.DataInit;
import jpabook.start.entity.Order;
import jpabook.start.entity.item.mapping.Item;

public class JQPL_NameGraphMain extends DataInit {

	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("JPQL에서 엔티티 그래프 힌트");
				em.createQuery("SELECT o FROM CH14_ORDERS o where o.id = :id", Order.class)
					.setParameter("id", 10L)
					.setHint("javax.persistence.fetchgraph", em.getEntityGraph("Order.withAll"))
					.getResultList().stream().forEach(order -> {						
						System.out.println(String.format("주문번호 : %s, 회원 이름 : %s"
							, order.getOrderId()
							, order.getMember().getName()));
						
						order.getOrderItem().stream().forEach(oi -> {
							Item item = oi.getItem();
							System.out.println(String.format("주문 상품 번호 : %s, 가격 : %s, 상품명 : %s"
								, oi.getId()
								, oi.getOrderPrice()
								, item.getName()));
						});						
					});					
				print.mainEndPrint();
			})
			.start();

	}

}
