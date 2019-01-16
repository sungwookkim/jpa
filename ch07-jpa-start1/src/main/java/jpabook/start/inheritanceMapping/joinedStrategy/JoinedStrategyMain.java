package jpabook.start.inheritanceMapping.joinedStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.util.Logic;
import common.util.Print;
import jpabook.start.inheritanceMapping.joinedStrategy.entity.Album;
import jpabook.start.inheritanceMapping.joinedStrategy.entity.Book;
import jpabook.start.inheritanceMapping.joinedStrategy.entity.Movie;

public class JoinedStrategyMain {

	/*
	 * 조인 전략
	 * 조인 전략(Joined Strategy)은 엔티티 각각을 모두 테이블로 만들고 자식 테이블이 부모 테이블의 기본 키를 받아서
	 * 기본 키 + 외래 키로 사용하는 전략이다.
	 * 따라서 조회할 때 자주 사용한다. 이 전략을 사용할 때 주의할 점이 있는데 객체는 타입으로 구분할 수 있지만 테이블은
	 * 타입의 개념이 없다. 따라서 타입을 구분하는 컬럼을 추가해야 한다.
	 * 여기서 DTYPE 컬럼을 구분 컬럼으로 사용한다.
	 * 
	 * 장점
	 * - 테이블이 정규화된다.
	 * - 외래 키 참조 무결성 제약조건을 활용할 수 있다.
	 * - 저장공간을 효율적으로 사용한다.
	 * 
	 * 단점
	 * - 조회할 때 조인이 많이 사용되므로 성능이 저하될 수 있다.
	 * - 조회 쿼리가 복잡하다. 
	 * - 데이터를 등록할 INSERT SQL을 두 번 실행한다. 
	 * 
	 * 특징
	 * JPA 표준 명세는 구분 컬럼을 사용하도록 하지만 하이버네이트를 포함한 몇 구현체는 구분 컬럼(@DiscrimintorColumn)없이도 동작한다.
	 *  
	 * 관련 어노테이션
	 * @PrimaartKeyJoinColumn, @DiscriminatorColumn, @DiscriminatorValue
	 */
	public static void main(String[] args) {

		Print print = new Print();
		
		new Logic()
			.logic((em, tx) -> {
				print.mainStartPrint("[joined strategy] 앨범, 영화, 책 저장");
				
				tx.begin();

				em.persist( new Album("sinnakeAlbum", "sinnake", 10_000));
				em.persist(new Movie("sinnakeDirector", "sinnakeActor", "sinnake", 100_000));
				em.persist(new Book("sinnakeAuthor", "sinnakeISBN", "sinnake", 100_000_000));
				
				tx.commit();
				
				print.mainEndPrint();
			})
			.commitAfter(em -> {
				print.mainStartPrint("[joined strategy] 앨범 조회");

				List<Album> albums = Optional.ofNullable(em.createQuery("select a from CH07_JOINED_STRATEGY_ALBUM a where a.artist = :artist", Album.class)
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
				
				print.mainEndPrint();


				print.mainStartPrint("[joined strategy] 영화 조회");

				List<Movie> movies = Optional.ofNullable(em.createQuery("select m from CH07_JOINED_STRATEGY_MOVIE m where m.director = :director", Movie.class)
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
				
				print.mainEndPrint();


				
				print.mainStartPrint("[joined strategy] 책 조회");

				List<Book> books = Optional.ofNullable(em.createQuery("select b from CH07_JOINED_STRATEGY_BOOK b where b.author = :author", Book.class)
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
				
				print.mainEndPrint();
			})
			.start();
	}

}
