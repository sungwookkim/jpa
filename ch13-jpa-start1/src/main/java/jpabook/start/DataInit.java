package jpabook.start;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import common.util.Logic;
import jpabook.start.entity.Category;
import jpabook.start.entity.CategoryItem;
import jpabook.start.entity.Delivery;
import jpabook.start.entity.DeliveryStatus;
import jpabook.start.entity.Member;
import jpabook.start.entity.Order;
import jpabook.start.entity.OrderItem;
import jpabook.start.entity.OrderStatus;
import jpabook.start.entity.embedded.Address;
import jpabook.start.entity.item.Album;
import jpabook.start.entity.item.Book;
import jpabook.start.entity.item.Movie;
import jpabook.start.entity.item.mapping.Item;

public class DataInit {

	public static void initSave() {
		new Logic()
		.logic((em, tx) -> {
			System.out.println("=============== JPQL 저장 ===============");
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
			em.persist(order1);
			
			em.persist(member2);			
			em.persist(order2);
			
			tx.commit();
			System.out.println("=========================================");
		})
		.start();		
	}
}
