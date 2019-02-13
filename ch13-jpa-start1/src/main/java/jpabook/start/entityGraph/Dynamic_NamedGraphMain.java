package jpabook.start.entityGraph;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.Subgraph;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.DataInit;
import jpabook.start.entity.Order;
import jpabook.start.entity.OrderItem;
import jpabook.start.entity.item.mapping.Item;

public class Dynamic_NamedGraphMain extends DataInit {

	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				/*
				 * 동적으로 엔티티 그래프를 구성하려면 createEntityGraph() 메소드를 사용한다.
				 */
				print.mainStartPrint("동적 엔티티 그래프");
				EntityGraph<Order> graph = em.createEntityGraph(Order.class);
				graph.addAttributeNodes("member");
				
				Map<String, Object> hints = new HashMap<>();
				hints.put("javax.persistence.fetchgraph", graph);
				
				Order order = em.find(Order.class, 10L, hints);							

				System.out.println(String.format("주문번호 : %s, 회원 이름 : %s"
					, order.getOrderId()
					, order.getMember().getName() ));				
				print.mainEndPrint();
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("동적 엔티티 그래프 subgraph");
				EntityGraph<Order> graph = em.createEntityGraph(Order.class);
				graph.addAttributeNodes("member");
				
				Subgraph<OrderItem> subgraph = graph.addSubgraph("orderItems");
				subgraph.addAttributeNodes("item");
				
				Map<String, Object> hints = new HashMap<>();
				hints.put("javax.persistence.fetchgraph", graph);
				
				Order order = em.find(Order.class, 10L, hints);							

				System.out.println(String.format("주문번호 : %s, 회원 이름 : %s"
						, order.getOrderId()
						, order.getMember().getName() ));
					
				order.getOrderItem().stream().forEach(oi -> {
					Item item = oi.getItem();
					
					System.out.println(String.format("주문 상품 번호 : %s, 가격 : %s, 상품명 : %s"
						, oi.getId()
						, oi.getOrderPrice()
						, item.getName() ));
				});
				print.mainEndPrint();
			})
			.start();

	}

}
