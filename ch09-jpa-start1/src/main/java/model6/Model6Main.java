package model6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import model6.entity.Category;
import model6.entity.CategoryItem;
import model6.entity.Delivery;
import model6.entity.DeliveryStatus;
import model6.entity.Member;
import model6.entity.Order;
import model6.entity.OrderItem;
import model6.entity.OrderStatus;
import model6.entity.embedded.Address;
import model6.entity.item.Album;
import model6.entity.item.Book;
import model6.entity.item.Movie;
import model6.entity.item.mapping.Item;

@SuppressWarnings({"unchecked", "rawtypes"})
public class Model6Main {

	public static void main(String[] args) {
		
		Print print = new Print();
		
		new Logic()
			.logic((em, tx) -> {
				print.mainStartPrint("카테고리, 상품, 회원, 주문 저장");

				tx.begin();
				
				/************/
				/* 카테고리 */
				/************/
				Category categoryAlbum = new Category("앨범");
				Category categoryBook = new Category("책");
				Category categoryMovie = new Category("영화");
				
				/**************/
				/* Album 상품 */
				/**************/
				List<Item> albumItems = Arrays.asList(
					new Album("노래", 10_000, 100, "sinnake_노래", "롹")
					, new Album("뮤직", 20_000, 200, "sinnake_뮤직", "뽤라드")
					, new Album("뜨로우트", 30_000, 300, "sinnake_뜨로우트", "뜨라트")
				);
				
				/************/
				/* Book 상품*/
				/************/
				List<Item> bookItems = Arrays.asList(
					new Book("자바8", 18_000, 100, "누군가", "10-10-10")
					, new Book("JPA", 28_000, 200, "사람이", "20-20-20")
					, new Book("동화책", 38_000, 100, "동심이", "30-30-30")
				);

				/************/
				/* Movie 상품*/
				/************/
				List<Item> movieItems = Arrays.asList(
					new Movie("아이쿠맨", 17_100, 100, "물고기맨", "섹시가이")
					, new Movie("뜨퐈이더뭰", 17_200, 200, "거미맨", "짬뽕")
					, new Movie("어반쨔스", 17_300, 300, "영웅들", "각종영웅")
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
				em.persist(categoryAlbum);
				em.persist(categoryBook);
				em.persist(categoryMovie);
				
				albumItems.stream()
					.map(ci -> setCategoryItem.apply(ci, categoryAlbum))
					.collect(Collectors.toList());
				
				bookItems.stream()
					.map(fi -> setCategoryItem.apply(fi, categoryBook))
					.collect(Collectors.toList());
				
				movieItems.stream()
					.map(fi -> setCategoryItem.apply(fi, categoryMovie))
					.collect(Collectors.toList());
				
				/*************/
				/* 주문 저장 */
				/*************/
				Member member1 = new Member("신나게1", "sinnake1", new Address( "서울1", "7번가1", "123456"));
				Delivery delivery1 = new Delivery(new Address( "서울1 회사", "7번가1 회사", "789101"), DeliveryStatus.READY);
				Order order1 = new Order(new Date(), OrderStatus.ORDER, member1, delivery1);
				
				Member member2 = new Member("신나게2", "sinnake2", new Address("서울2", "7번가2", "789456"));
				Delivery delivery2 = new Delivery(new Address( "서울2 회사", "7번가2 회사", "789111"), DeliveryStatus.READY);
				Order order2 = new Order(new Date(), OrderStatus.ORDER, member2, delivery2);
				
				BiFunction<Item, Order, OrderItem> setOrderItem = (i, o) -> {
					OrderItem orderItem = new OrderItem(i, o, i.getPrice(), i.getStockquantity() - 1);
					
					o.addOrderItem(orderItem);
					/*
					 * 영속성 전이 상태 전에는 아래와 같이 영속성을 추가 해줘야 했다.
					 */
					//em.persist(orderItem);

					return orderItem;
				};
								
				// sinnake1 회원 앨범 상품 구매.
				albumItems.stream()
					.map(ci -> setOrderItem.apply(ci, order1))
					.collect(Collectors.toList());

				// sinnake1 회원 책 상품 구매.
				bookItems.stream()
					.map(ci -> setOrderItem.apply(ci, order1))
					.collect(Collectors.toList());

				// sinnake1 회원 영화 상품 구매.
				movieItems.stream()
					.map(ci -> setOrderItem.apply(ci, order1))
					.collect(Collectors.toList());

				// sinnake2 회원 앨범 상품 구매.
				albumItems.stream()
					.map(ci -> setOrderItem.apply(ci, order2))
					.collect(Collectors.toList());

				// sinnake2 회원 책 상품 구매.
				bookItems.stream()
					.map(ci -> setOrderItem.apply(ci, order2))
					.collect(Collectors.toList());
				
				// sinnake2 회원 영화 상품 구매.
				movieItems.stream()
					.map(ci -> setOrderItem.apply(ci, order2))
					.collect(Collectors.toList());
				
				em.persist(member1);
				/*
				 * 영속성 전이 상태 전에는 아래와 같이 영속성을 추가 해줘야 했다.
				 */
				//em.persist(delivery1);
				em.persist(order1);
				
				em.persist(member2);
				/*
				 * 영속성 전이 상태 전에는 아래와 같이 영속성을 추가 해줘야 했다.
				 */
				//em.persist(delivery2);
				em.persist(order2);
				
				tx.commit();
				
				print.mainEndPrint();
			})
			.start();
			
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("sinnake1 회원이 구매한 전체 상품 내역");				
				
				List<Order> order = Optional.ofNullable(em.createQuery("select o from CH09_MODEL6_ORDERS o where o.member.userId = :userId", Order.class)									
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
									Address address = Optional.ofNullable(delivery.getAddress()).orElseGet(Address::new);
									
									System.out.println(String.format("[배송 정보] 도시 : %s, 거리 : %s, 우편번호 : %s, 상태 : %s" 
										, Optional.ofNullable(address.getCity()).orElse("")
										, Optional.ofNullable(address.getStreet()).orElse("")									
										, Optional.ofNullable(address.getZipCode()).orElse("")
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
				
				
								
				print.mainStartPrint("sinnake1 회원이 구매한 전체 상품 내역");
				System.out.println("===============  ===============");
				List<OrderItem> orderItems = em.createQuery("select o from CH09_MODEL6_ORDER_ITEM o where o.order.member.userId = 'sinnake1'", OrderItem.class)
					.getResultList();

				BiFunction<Class, Map<Class, List<Item>>, List> getData = (clazz, itemMap) -> {
					return itemMap.get(clazz).stream()
							.map(a -> clazz.cast(a))
							.collect(Collectors.toList());
				};
				
				Map<Class, List<Item>> items3 = orderItems.stream()
						.map(i -> i.getItem())
						.collect(Collectors.groupingBy(Item::getClass));

				List<Album> albums = getData.apply(Album.class, items3);
				albums.stream().forEach(a -> {
					System.out.println(String.format("id : %s, name : %s, stockquantity : %s, price : %s, artist : %s, etc : %s"
						, Optional.ofNullable(a.getId()).orElse(-1L)
						, Optional.ofNullable(a.getName()).orElse("")
						, Optional.ofNullable(a.getStockquantity()).orElse(-1)
						, Optional.ofNullable(a.getPrice()).orElse(-1)
						, Optional.ofNullable(a.getArtist()).orElse("")
						, Optional.ofNullable(a.getEtc()).orElse("") ));
				});
				
				List<Book> books = getData.apply(Book.class, items3);
				books.stream().forEach(b -> {
					System.out.println(String.format("id : %s, name : %s, stockquantity : %s, price : %s, author : %s, isbn : %s"
						, Optional.ofNullable(b.getId()).orElse(-1L)
						, Optional.ofNullable(b.getName()).orElse("")
						, Optional.ofNullable(b.getStockquantity()).orElse(-1)
						, Optional.ofNullable(b.getPrice()).orElse(-1)
						, Optional.ofNullable(b.getAuthor()).orElse("")
						, Optional.ofNullable(b.getIsbn()).orElse("") ));					
				});
				
				List<Movie> movies = getData.apply(Movie.class, items3);
				movies.stream().forEach(m -> {
					System.out.println(String.format("id : %s, name : %s, stockquantity : %s, price : %s, director : %s, actor : %s"
						, Optional.ofNullable(m.getId()).orElse(-1L)
						, Optional.ofNullable(m.getName()).orElse("")
						, Optional.ofNullable(m.getStockquantity()).orElse(-1)
						, Optional.ofNullable(m.getPrice()).orElse(-1)
						, Optional.ofNullable(m.getDirector()).orElse("")
						, Optional.ofNullable(m.getActor()).orElse("") ));					
				});
				System.out.println("=====================================================================");
				
			})
			.start();				
	}
}
