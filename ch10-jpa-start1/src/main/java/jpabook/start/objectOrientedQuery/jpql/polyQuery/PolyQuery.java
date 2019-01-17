package jpabook.start.objectOrientedQuery.jpql.polyQuery;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Member;
import jpabook.start.objectOrientedQuery.entity.item.Album;
import jpabook.start.objectOrientedQuery.entity.item.Book;
import jpabook.start.objectOrientedQuery.entity.item.Movie;
import jpabook.start.objectOrientedQuery.entity.item.abs.Item;

public class PolyQuery extends DataInit {

	/*
	 * 다형성 쿼리
	 */
	public static void main(String[] args) {
		initSave();

		Print print = new Print();
		Print subPrint = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.logic((em, tx) -> {
				print.mainStartPrint("다형성 쿼리 저장");
				tx.begin();
				
				Album album = new Album("artist", "album", 10_000);
				Book book = new Book("author", "isbn", "book", 20_000);
				Movie movie = new Movie("director", "actor", "movie", 30_000);
				
				em.persist(album);
				em.persist(book);
				em.persist(movie);
				
				tx.commit();
				print.mainEndPrint();
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("다형성 쿼리 조회");

				subPrint.subStartPrint("Item 조회");
				em.createQuery("SELECT i FROM CH10_ITEM i", Item.class)
					.getResultList().stream().forEach(i -> {
						System.out.println(String.format("item id : %s, item name : %s, item price : %s"
							, i.getId()
							, i.getName()
							, i.getPrice() ));
					});
				subPrint.subEndPrint();
				
				
				
				/*
				 * TYPE
				 * 엔티티의 상속 구조에서 조회 대상을 특정 자식 타입으로 한정할 때 주로 사용한다.
				 */
				subPrint.subStartPrint("TYPE");
				em.createQuery("SELECT i"
					+ " FROM CH10_ITEM i"
					+ " WHERE type(i) IN (CH10_BOOK, CH10_MOVIE)", Item.class)
				.getResultList().stream().forEach(i -> {
					System.out.println(String.format("item id : %s, item name : %s, item price : %s"
							, i.getId()
							, i.getName()
							, i.getPrice() ));						
				});
				subPrint.subEndPrint();
				
				
				
				/*
				 * TREAT(JPA2.1)
				 * TREAT는 JPA 2.1에 추가된 기능인데 자바의 타입 캐스팅과 비슷하다.
				 * 상속 구조에서 부모 타입을 특정 자식 타입으로 다룰 때 사용한다.
				 * JPA 표준은 FROM, WHERE 절에서 사용할 수 있지만, 하이버네이트는
				 * SELECT 절에서도 TREAT를 사용할 수 있따.
				 */
				subPrint.subStartPrint("TREAT(JPA2.1)");
				em.createQuery("SELECT i"
					+ " FROM CH10_ITEM i"
					+ " WHERE treat(i as CH10_BOOK).author = :author", Item.class)
				.setParameter("author", "author").getResultList().stream().forEach(i -> {
					System.out.println(String.format("item id : %s, item name : %s, item price : %s"
							, i.getId()
							, i.getName()
							, i.getPrice() ));						
				});
				subPrint.subEndPrint();

				
				
				/*
				 * 사용자 정의 함수 호출(JPA2.1)
				 * JPA2.1 부터 사용자 정의 함수를 지원한다.
				 * 문법 : 
				 * 	function_invocation::= FUNCTION(function_name, {, function_arg}*)
				 * 예 :
				 *  select function('group_concat', i.name) from CH10_ITEM i
				 * 
				 * 하이버네이트 구현체를 사용해서 방언 클래스를 상속해서 구현하고 사용할 데이터베이스 함수를
				 * 미리 등록해야 한다.
				 * 
				 * persistence.xml 파일에 방언을 아래와 같이 등록해야 한다.
				 * <properties>
				 * 		<property name="hibernate.dialect" value="jpabook.start.objectOrientedQuery.Myh2Dialect"/>
				 * </properties>
				 */
				subPrint.subStartPrint("사용자 정의 함수 호출(JPA2.1)");
				em.createQuery("SELECT group_concat(i.name)"
						+ " FROM CH10_ITEM i", String.class)
				.getResultList().stream().forEach(i -> {
					System.out.println(String.format("item name : %s", i));
				});
				subPrint.subEndPrint();

				
				
				/*
				 * Named 쿼리: 정적 쿼리
				 * JPQL 쿼리는 크게 동적 쿼리, 정적 쿼리로 나눌 수 있다.
				 * 
				 * ㅁ 동적 쿼리 :
				 * 	em.createQuery("select ...") 처럼 JPQL을 문자로 완성해서 직접 넘기는 것을 동적 쿼리라 한다.
				 * 	런타임에 특정 조건에 따라 JPQL을 동적으로 구성할 수 있다.
				 * ㅁ 정적 쿼리 :
				 * 	미리 정의한 쿼리에 이름을 부여해서 필요할 대 사용할 수 있는데 이것을 Named 쿼리라 한다.
				 * 	Named 쿼리는 한 번 정의하면 변경할 수 없는 정적인 쿼리다.
				 * 
				 * Named 쿼리는 애플리케이션 로딩 시점에 JPQL 문법을 체크하고 미리 파싱해둔다. 
				 * 따라서 오류를 빨리 확인할 수 있고, 사용하는 시점에는 파싱된 결과를 재사용하므로 성능상 이점도 있다.
				 * 그리고 Named 쿼리는 변하지 않는 정적 SQL이 생성되므로 데이터베이스의 조회 성능 최적화에도 도움이 된다.
				 * Named 쿼리는 @NamedQuery 어노테이션을 사용해서 자바 코드에 작성하거나 XML 문서에 작성할 수 있다.
				 * 
				 * Named 쿼리를 어노테이션에 정의
				 * 	Named 쿼리는 이름 그대로 쿼리에 이름을 부여해서 사용하는 방법이다.
				 * 	참고
				 * 		Named 쿼리 이름을 간단히 findByUserName이라 하지 않고 CH10_OOQ_MEMBER.findByUserName처럼 앞에 엔티티 이름을
				 * 		주었는데 이것은 기능적으로 특별한 의미가 있는 것은 아니다.
				 * 		하지만 Named 쿼리는 영속성 유닛 단위로 관리되므로 충돌을 방자하기 위해 엔티티 이름을 앞에 주었다.
				 *		그리고 엔티티 이름이 앞에 있으면 관리하기가 쉽다.
				 * 
				 * 	하나의 엔티티에 2개 이상의 Named 쿼리를 정의하려면 @NamedQueries 어노테이션을 사용한다.
				 * 
				 * 	@NamedQuery, @NamedQueries 어노테이션 설정은 jpabook.start.objectOrientedQuery.jpql.entity.Member 클래스 파일에 되어있다.
				 * 
				 * Named 쿼리를 XML에 정의
				 * 	JAP에서 어노테이션으로 작성할 수 있는 것은 XML로도 작성할 수 있다.
				 * 	물론 어노테이션을 사용하는 것이 직관적이고 편리하다. 하지만 Named 쿼리를 작성할 때는 XML을 사용하는 것이 더 편하다.
				 * 	자바 언어로 멀티라인 문자를 다루는 것은 상당히 귀찮은 일이다.
				 * 
				 * 환경에 따른 설정
				 * 	만약에 XML과 어노테이션에 같은 설정이 있으면 "XML이 우선권"을 가진다.
				 * 	예를 들어 같은 이름의 Named 쿼리가 있으면 XML에 정의한 것이 사용된다. 따라서 애플리케이션이 운영 환경에
				 * 	따라 다른 쿼리를 실행해야 한다면 각 환경에 맞춘 XML을 준비해 두고 XML만 변경해서 배포하면 된다.
				 * 		
				 * META-INF\ormMember.xml 파일에 쿼리를 작성하고 \META-INF\persistence.xml 파일에 작성한 ormMember.xml 파일을 등록해야 한다. 
				 */
				subPrint.subStartPrint("[@NamedQuery] Named 쿼리: 정적 쿼리");
				em.createNamedQuery("CH10_OOQ_MEMBER.findByUserName", Member.class)
					.setParameter("userName", "sinnake1").getResultList().stream().forEach(m -> {
						System.out.println(String.format("member id : %s, member username : %s, member age : %s"
							, m.getId()
							, m.getUserName()
							, m.getAge() ));
					});
				subPrint.subEndPrint();
				
				
				
				subPrint.subStartPrint("[@NamedQueries] Named 쿼리: 정적 쿼리");
				em.createNamedQuery("CH10_OOQ_MEMBER.count", Long.class)
					.getResultList().stream().forEach(c -> {
						System.out.println(String.format("member count : %s", c));
					});
				
				em.createNamedQuery("CH10_OOQ_MEMBER.findByUserId", Member.class)
					.setParameter("id", 2L).getResultList().stream().forEach(m -> {
						System.out.println(String.format("member id : %s, member username : %s, member age : %s"
							, m.getId()
							, m.getUserName()
							, m.getAge() ));
					});				
				subPrint.subEndPrint();
				
				
				
				subPrint.subStartPrint("[xml] Named 쿼리: 정적 쿼리");
				em.createNamedQuery("xml.CH10_OOQ_MEMBER.findByUserName", Member.class)
					.setParameter("userName", "sinnake2").getResultList().stream().forEach(m -> {
						System.out.println(String.format("member id : %s, member username : %s, member age : %s"
							, m.getId()
							, m.getUserName()
							, m.getAge() ));
					});
				
				em.createNamedQuery("xml.CH10_OOQ_MEMBER.count", Long.class)
					.getResultList().stream().forEach(c -> {
						System.out.println(String.format("member count : %s", c));
					});
				subPrint.subEndPrint();
				
				print.mainEndPrint();
			})
			.start();
	}

}
