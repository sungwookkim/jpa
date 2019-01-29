package jpabook.start.inheritanceMapping.singleTableStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.util.Logic;
import common.util.Print;
import jpabook.start.inheritanceMapping.singleTableStrategy.entity.Album;
import jpabook.start.inheritanceMapping.singleTableStrategy.entity.Book;
import jpabook.start.inheritanceMapping.singleTableStrategy.entity.Movie;

public class SingleTableStrategyMain {

	/*
	 * 단일 테이블 전략
	 * 
	 * 이름 그대로 테이블을 하나만 사용한다. 그리고 구분 컬럼(여기선 DTYPE)으로 어떤 자식 데이터가 저장되었는지
	 * 구분한다. 조회할 때 조인을 사용하지 않으므로 일반적으로 가장 빠르다.
	 * 이 전략을 사용할 때 주의점은 자식 엔티티가 매핑한 컬럼은 모두 null을 허용해야 한다는 점이다.
	 * 
	 * 장점
	 * - 조인이 필요 없으므로 일반적으로 조회 성능이 빠르다.
	 * - 조회 쿼리가 단순하다.
	 * 
	 * 단점
	 * - 자식 엔티티가 매핑한 컬럼은 모두 null을 허용해야 한다.
	 * - 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있다.
	 * 그러므로 상황에 따라서는 조회 성능이 오히려 느려질 수 있다.
	 * 
	 * 특징
	 * - 구분 컬럼을 꼭 사용해야 한다. 따라서 @DiscriminatorColumn을 꼭 설정해야 한다.
	 * - @DiscriminatorValue를 지정하지 않으면 기본으로 엔티티 이름을 사용한다.
	 */
	public static void main(String[] args) {

		Print print = new Print();
		
		new Logic()
			.logic((em, tx) -> {
				print.mainStartPrint("[singleTable strategy] 앨범, 영화, 책 저장");
				
				tx.begin();

				em.persist(new Album("sinnakeAlbum", "sinnake", 10_000));
				em.persist(new Movie("sinnakeDirector", "sinnakeActor", "sinnake", 100_000));
				em.persist(new Book("sinnakeAuthor", "sinnakeISBN", "sinnake", 100_000_000));
				
				tx.commit();
				
				print.mainEndPrint();
			})
			.commitAfter(em -> {
				print.mainStartPrint("[singleTable strategy] 앨범 조회");

				List<Album> albums = Optional.ofNullable(em.createQuery("select a from CH07_SINGLETABLE_STRATEGY_ALBUM a where a.artist = :artist", Album.class)
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
				
				
				print.mainStartPrint("[singleTable strategy] 영화 조회");

				List<Movie> movies = Optional.ofNullable(em.createQuery("select m from CH07_SINGLETABLE_STRATEGY_MOVIE m where m.director = :director", Movie.class)
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
				
				
				
				print.mainStartPrint("[singleTable strategy] 책 조회");

				List<Book> books = Optional.ofNullable(em.createQuery("select b from CH07_SINGLETABLE_STRATEGY_BOOK b where b.author = :author", Book.class)
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
