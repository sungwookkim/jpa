package jpabook.start.inheritanceMapping.tablePerConcreteClassStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.util.Logic;
import jpabook.start.inheritanceMapping.tablePerConcreteClassStrategy.entity.Album;
import jpabook.start.inheritanceMapping.tablePerConcreteClassStrategy.entity.Book;
import jpabook.start.inheritanceMapping.tablePerConcreteClassStrategy.entity.Movie;

public class TablePerConcreteClassStrategyMain {

	/*
	 * 구현 클래스마다 테이블 전략
	 * 
	 * 그현 클래스마다 테이블 전략(Table-per-Concrete-Class Strategy)은 자식 엔티티마다 테이블을 만든다.
	 * 그리고 테이블 각각에 필요한 컬럼이 모두 있다.
	 * 이 전략은 자식 엔티티마다 테이블을 만든다. 일반적으로 추천하지 않는 전략이다.
	 * 
	 * 장점
	 * - 서브 타입을 구분해서 처리할 때 효과적이다.
	 * - not null 제약조건을 사용할 수 있다.
	 * 
	 * 단점
	 * - 여러 자식 테이블을 함께 조회할 때 성능이 느리다.(SQL에 UNION을 사용해야 한다.)
	 * - 자식 테이블을 통합해서 쿼리하기 어렵다.
	 * 
	 * 특징
	 * - 구분 컬럼을 사용하지 않는다.
	 */
	public static void main(String[] args) {

		new Logic()
			.logic((em, tx) -> {
				System.out.println("=============== [table_per_concrete_class strategy] 앨범, 영화, 책 저장 ===============");				
				tx.begin();

				em.persist(new Album("sinnakeAlbum", "sinnake", 10_000));
				em.persist(new Movie("sinnakeDirector", "sinnakeActor", "sinnake", 100_000));
				em.persist(new Book("sinnakeAuthor", "sinnakeISBN", "sinnake", 100_000_000));
				
				tx.commit();
				System.out.println("=======================================================================================");
			})
			.commitAfter(em -> {
				System.out.println("=============== [table_per_concrete_class strategy] 앨범 조회 ===============");

				List<Album> albums = Optional.ofNullable(em.createQuery("select a from CH07_TABLE_PER_CONCRETE_CLASS_STRATEGY_ALBUM a where a.artist = :artist", Album.class)
					.setParameter("artist", "sinnakeAlbum")
					.getResultList())
				.filter(a -> a.size() > 0)
				.orElseGet(ArrayList::new);

				albums.stream().forEach(a -> {
					System.out.println( String.format("ID : %s, 아티스트 : %s, 이름 : %s, 가격 : %s"
						, Optional.ofNullable(a.getId()).map(String::valueOf).orElse("")
						, Optional.ofNullable(a.getArtist()).orElse("")
						, Optional.ofNullable(a.getName()).orElse("")
						, Optional.ofNullable(a.getPrice()).map(String::valueOf).orElse("") ));
				});
				
				System.out.println("=============================================================================");
				
				System.out.println("=============== [table_per_concrete_class strategy] 영화 조회 ===============");

				List<Movie> movies = Optional.ofNullable(em.createQuery("select m from CH07_TABLE_PER_CONCRETE_CLASS_STRATEGY_MOVIE m where m.director = :director", Movie.class)
					.setParameter("director", "sinnakeDirector")
					.getResultList())
				.filter(m -> m.size() > 0)
				.orElseGet(ArrayList::new);

				movies.stream().forEach(m -> {
					System.out.println( String.format("ID : %s, 감독 : %s, 배우 : %s, 영화이름 : %s, 가격 : %s"
						, Optional.ofNullable(m.getId()).map(String::valueOf).orElse("")
						, Optional.ofNullable(m.getDirector()).orElse("")
						, Optional.ofNullable(m.getActor()).orElse("")
						, Optional.ofNullable(m.getName()).orElse("")
						, Optional.ofNullable(m.getPrice()).map(String::valueOf).orElse("") ));
				});
				
				System.out.println("=============================================================================");
				
				System.out.println("=============== [table_per_concrete_class strategy] 책 조회 ===============");

				List<Book> books = Optional.ofNullable(em.createQuery("select b from CH07_TABLE_PER_CONCRETE_CLASS_STRATEGY_BOOK b where b.author = :author", Book.class)
					.setParameter("author", "sinnakeAuthor")
					.getResultList())
				.filter(a -> a.size() > 0)
				.orElseGet(ArrayList::new);

				books.stream().forEach(b -> {
					System.out.println( String.format("ID : %s, 저자 : %s, ISBN : %s, 책 이름 : %s, 가격 : %s"
						, Optional.ofNullable(b.getId()).map(String::valueOf).orElse("")
						, Optional.ofNullable(b.getAuthor()).orElse("")
						, Optional.ofNullable(b.getIsbn()).orElse("")
						, Optional.ofNullable(b.getName()).orElse("")
						, Optional.ofNullable(b.getPrice()).map(String::valueOf).orElse("") ));
				});
				
				System.out.println("===========================================================================");
			})
			.start();
	}

}
