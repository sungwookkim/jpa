package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.OrderSearch;
import domain.entity.Delivery;
import domain.entity.Member;
import domain.entity.Order;
import domain.entity.OrderItem;
import domain.entity.item.abs.Item;
import domain.entity.status.DeliveryStatus;
import repository.MemberRepository;
import repository.OrderRepository;

@Service
@Transactional
public class OrderService {
	
	@Autowired MemberRepository memberRepository;
	@Autowired OrderRepository orderRepository;
	@Autowired ItemService itemService;
	
	public Long order(Long memberId, Long itemId, int count) {

		return this.order(memberId, (id, key) -> {
			List<Map<String, Long>> rtnValue = new ArrayList<>();
			
			rtnValue.add(new HashMap<String, Long>() {
				private static final long serialVersionUID = 1L;
				
				{
					put(id, itemId);
					put(key, (long) count);
				}
			});
			
			return rtnValue;
		});
	}

	public Long order(Long memberId, BiFunction<String, String, List<Map<String, Long>>> itemInfo) {
		final String itemId = "ITEM_ID";
		final String itemCnt = "ITEM_COUNT";
		
		// 엔티티 조회
		Member member = memberRepository.findoOne(memberId);

		// 배송정보 생성
		Delivery delivery = new Delivery(member.getAddress(), DeliveryStatus.READY);
		
		// 주문상품 생성
		List<OrderItem> orderItems = itemInfo.apply(itemId, itemCnt).stream()
			.map(i -> {
				Item item = itemService.findItem(i.get(itemId));			
				OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), i.get(itemCnt).intValue());
				
				return orderItem;
			})
			.collect(Collectors.toList());
		
		// 주문 생성
		Order order = Order.createOrder(member, delivery, orderItems);
		
		// 주문 저장
		orderRepository.save(order);
		
		return order.getId();
	}
	
	public void cancelOrder(Long orderId) {
		Order order = orderRepository.findOne(orderId);
		
		order.cancle();
	}

	public void complete(Long orderId) {
		Order order = orderRepository.findOne(orderId);

		order.complete();
	}

	public List<Order> findOrders(OrderSearch orderSearch) {
		return orderRepository.findAll(orderSearch);
	}
}
