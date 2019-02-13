package jpabook.start.entityGraph;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityGraph;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.DataInit;
import jpabook.start.entity.Order;
import jpabook.start.entity.item.mapping.Item;

public class NamedGraphMain extends DataInit {

	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("em.find()에서 엔티티 그래프 사용.");
				EntityGraph<?> entityGraph = em.getEntityGraph("Order.withMember");
				
				Map<String, Object> hints = new HashMap<>();
				hints.put("javax.persistence.fetchgraph", entityGraph);
				
				Order order = em.find(Order.class, 10L, hints);							

				System.out.println(String.format("주문번호 : %s, 회원 이름 : %s"
					, order.getOrderId()
					, order.getMember().getName() ));
				print.mainEndPrint();
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("subgraph");
				EntityGraph<?> entityGraph = em.getEntityGraph("Order.withAll");
				
				Map<String, Object> hints = new HashMap<>();
				hints.put("javax.persistence.fetchgraph", entityGraph);
				
				Order order = em.find(Order.class, 10L, hints);							

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
				print.mainEndPrint();
			})
			.start();
	}

}
