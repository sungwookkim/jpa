import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.OrderSearch;
import domain.embedded.Address;
import domain.entity.Member;
import domain.entity.Order;
import domain.entity.item.Book;
import domain.entity.item.abs.Item;
import domain.entity.status.DeliveryStatus;
import domain.entity.status.OrderStatus;
import repository.OrderRepository;
import service.OrderService;
import web.db.DBConfig;
import web.db.JpaConfig;
import web.db.vendor.h2.H2Config;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
classes = {
	H2Config.class
	, DBConfig.class
	, JpaConfig.class
	, AppConfig.class
})
@Transactional
public class OrderServiceTest {

	@PersistenceContext
	EntityManager em;
	
	@Autowired OrderService orderService;
	@Autowired OrderRepository orderRepository;
	
	@Test
	public void 상품주문() throws Exception {
		Member member = createMember();		
		Item item = createBook("시골 JPA", 10_000, 10);
		int orderCnt = 2;
		
		Long orderId = orderService.order(member.getId(), item.getId(), orderCnt);
		
		Order getOrder = orderRepository.findById(orderId).orElseGet(Order::new);
		
		assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
		assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
		assertEquals("주문 가격은 가격 * 수량이다.", 10000 * 2, getOrder.getTotalPrice());
		assertEquals("주문 수량만큼 재고가 줄어야 한다.", 8, item.getStockQuantity());
	}
	
	@Test
	public void 여러상품주문() throws Exception {
		Member member = createMember();
		Item jpa = createBook("시골 JPA", 10_000, 10);
		Item java8 = createBook("java8", 20_000, 9);
		
		Long orderId = orderService.order(member.getId(), (id, key) -> {
			List<Map<String, Long>> rtnValue = new ArrayList<>();
			
			rtnValue.add(new HashMap<String, Long>() {
				private static final long serialVersionUID = 1L;
				
				{
					put(id, jpa.getId());
					put(key, 2L);
				}
			});
			
			rtnValue.add(new HashMap<String, Long>() {
				private static final long serialVersionUID = 1L;

				{
					put(id, java8.getId());
					put(key, 3L);
				}
			});
			
			return rtnValue;
		});
		
		Order getOrder = orderRepository.findById(orderId).orElseGet(Order::new);

		assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());		
		assertEquals("주문한 상품 종류 수가 정확해야 한다.", 2, getOrder.getOrderItems().size());
		assertEquals("주문 가격은 가격 * 수량이다.", (10_000 * 2) + (20_000 * 3), getOrder.getTotalPrice());
		assertEquals("JPA 주문 수량만큼 재고가 줄어야 한다.", 8, jpa.getStockQuantity());
		assertEquals("JAVA8 주문 수량만큼 재고가 줄어야 한다.", 6, java8.getStockQuantity());
	}
	
	@Test(expected = IllegalStateException.class)
	public void 상품주문_재고수량초과() throws IllegalStateException {
		Member member = createMember();		
		Item item = createBook("시골 JPA", 10_000, 10);
		int orderCnt = 11;
		
		orderService.order(member.getId(), item.getId(), orderCnt);
		
		fail("재고 수량 부족 예외가 발생해야 한다.");
	}

	@Test
	public void 주문취소() {
		Member member = createMember();		
		Item item = createBook("시골 JPA", 10_000, 10);
		int orderCnt = 2;
		
		Long orderId = orderService.order(member.getId(), item.getId(), orderCnt);
		
		orderService.cancelOrder(orderId);
		
		Order order = orderRepository.findById(orderId).orElseGet(Order::new);
		
		assertEquals("주문 취소시 상태는 CANCLE 이다.", OrderStatus.CANCEL, order.getStatus());
		assertEquals("주문이 취소된 상품은 그만큼 재고가 증가해야 한다.", 10, item.getStockQuantity());
	}

	@Test
	public void 주문조회_ORDER() {
		Member member = createMember();
		Item jpa = createBook("시골 JPA", 10_000, 10);
		Item java8 = createBook("java8", 20_000, 9);
		
		orderService.order(member.getId(), (id, key) -> {
			List<Map<String, Long>> rtnValue = new ArrayList<>();
			
			rtnValue.add(new HashMap<String, Long>() {
				private static final long serialVersionUID = 1L;
				
				{
					put(id, jpa.getId());
					put(key, 2L);
				}
			});
			
			rtnValue.add(new HashMap<String, Long>() {
				private static final long serialVersionUID = 1L;

				{
					put(id, java8.getId());
					put(key, 3L);
				}
			});
			
			return rtnValue;
		});

		List<Order> orders = orderService.findOrders(new OrderSearch("sinnake", OrderStatus.ORDER));
		
		assertEquals("주문 데이터가 1건 이여야 한다.", 1, orders.size());
		assertEquals("주문된 상품은 2건 이여야 한다.", 2, orders.get(0).getOrderItems().size());
	}
	
	@Test
	public void 주문조회_CANCLE() {
		Member member = createMember();
		Item jpa = createBook("시골 JPA", 10_000, 10);
		Item java8 = createBook("java8", 20_000, 9);
		
		Long orderId = orderService.order(member.getId(), (id, key) -> {
			List<Map<String, Long>> rtnValue = new ArrayList<>();
			
			rtnValue.add(new HashMap<String, Long>() {
				private static final long serialVersionUID = 1L;
				
				{
					put(id, jpa.getId());
					put(key, 2L);
				}
			});
			
			rtnValue.add(new HashMap<String, Long>() {
				private static final long serialVersionUID = 1L;

				{
					put(id, java8.getId());
					put(key, 3L);
				}
			});
			
			return rtnValue;
		});

		orderService.cancelOrder(orderId);
		List<Order> orders = orderService.findOrders(new OrderSearch("sinnake", OrderStatus.CANCEL));
		
		assertEquals("주문 취소된 데이터가 1건 이여야 한다.", 1, orders.size());
		assertEquals("주문 취소된 상품은 2건 이여야 한다.", 2, orders.get(0).getOrderItems().size());
		assertEquals("주문 취소된 첫 번째 상품의 이름은 시골 JPA 이다."
			, "시골 JPA"
			, orders.get(0).getOrderItems().get(0).getItem().getName());
		assertEquals("주문 취소된 첫 번째 상품의 이름은 java8 이다."
			, "java8"
			, orders.get(0).getOrderItems().get(1).getItem().getName());
	}
	
	@Test
	public void 주문조회_ORDER_spec() {
		Member member = createMember();
		Item jpa = createBook("시골 JPA", 10_000, 10);
		Item java8 = createBook("java8", 20_000, 9);
		
		orderService.order(member.getId(), (id, key) -> {
			List<Map<String, Long>> rtnValue = new ArrayList<>();
			
			rtnValue.add(new HashMap<String, Long>() {
				private static final long serialVersionUID = 1L;
				
				{
					put(id, jpa.getId());
					put(key, 2L);
				}
			});
			
			rtnValue.add(new HashMap<String, Long>() {
				private static final long serialVersionUID = 1L;

				{
					put(id, java8.getId());
					put(key, 3L);
				}
			});
			
			return rtnValue;
		});

		List<Order> orders = orderService.findOrders_spec(new OrderSearch("sinnake", OrderStatus.ORDER));
		
		assertEquals("주문 데이터가 1건 이여야 한다.", 1, orders.size());
		assertEquals("주문된 상품은 2건 이여야 한다.", 2, orders.get(0).getOrderItems().size());
	}
	
	@Test
	public void 주문조회_CANCLE_spec() {
		Member member = createMember();
		Item jpa = createBook("시골 JPA", 10_000, 10);
		Item java8 = createBook("java8", 20_000, 9);
		
		Long orderId = orderService.order(member.getId(), (id, key) -> {
			List<Map<String, Long>> rtnValue = new ArrayList<>();
			
			rtnValue.add(new HashMap<String, Long>() {
				private static final long serialVersionUID = 1L;
				
				{
					put(id, jpa.getId());
					put(key, 2L);
				}
			});
			
			rtnValue.add(new HashMap<String, Long>() {
				private static final long serialVersionUID = 1L;

				{
					put(id, java8.getId());
					put(key, 3L);
				}
			});
			
			return rtnValue;
		});

		orderService.cancelOrder(orderId);
		List<Order> orders = orderService.findOrders_spec(new OrderSearch("sinnake", OrderStatus.CANCEL));
		
		assertEquals("주문 취소된 데이터가 1건 이여야 한다.", 1, orders.size());
		assertEquals("주문 취소된 상품은 2건 이여야 한다.", 2, orders.get(0).getOrderItems().size());
		assertEquals("주문 취소된 첫 번째 상품의 이름은 시골 JPA 이다."
			, "시골 JPA"
			, orders.get(0).getOrderItems().get(0).getItem().getName());
		assertEquals("주문 취소된 첫 번째 상품의 이름은 java8 이다."
			, "java8"
			, orders.get(0).getOrderItems().get(1).getItem().getName());
	}
	
	@Test
	public void 배송완료() {
		Member member = createMember();		
		Item item = createBook("시골 JPA", 10_000, 10);
		int orderCnt = 2;
		
		Long orderId = orderService.order(member.getId(), item.getId(), orderCnt);

		orderService.complete(orderId);
		
		Order order = orderRepository.findById(orderId).orElseGet(Order::new);

		assertEquals("배송이 완료 상태가 되어야 한다.", DeliveryStatus.COMP, order.getDelivery().getStatus());	
	}

	@Test(expected = RuntimeException.class)
	public void 배송완료_취소불가능() throws RuntimeException {
		Member member = createMember();
		Item item = createBook("시골 JPA", 10_000, 10);
		int orderCnt = 2;
		
		Long orderId = orderService.order(member.getId(), item.getId(), orderCnt);

		orderService.complete(orderId);
		orderService.cancelOrder(orderId);

		fail("배송이 완료된 건은 취소가 되면 안된다.");	
	}
	
	private Member createMember() {
		Member member = new Member("sinnake", new Address("city", "street", "zipCode"));
		em.persist(member);
		
		return member;
	}
	
	private Book createBook(String name, int price, int stockquantity) {
		Book book = new Book(name, price, stockquantity, "sinnake", "isbn");
		em.persist(book);

		return book;
	}
}
