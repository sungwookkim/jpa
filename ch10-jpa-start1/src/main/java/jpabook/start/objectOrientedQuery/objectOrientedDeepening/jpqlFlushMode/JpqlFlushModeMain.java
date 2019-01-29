package jpabook.start.objectOrientedQuery.objectOrientedDeepening.jpqlFlushMode;

import javax.persistence.FlushModeType;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Product;

public class JpqlFlushModeMain extends DataInit {

	/*
	 * JPQL과 플러시 모드
	 * 
	 * 플러시는 영속성 컨텍스트의 변경 내역을 데이터베이스에 동기화 하는 것이다.
	 * JPA는 플러시가 일어날 때 영속성 컨텍스트에 등록, 수정, 삭제한 엔티티를 찾아서
	 * INSERT, UPDATE, DELETE SQL을 만들어 데이터베이스에 반영한다.
	 * 플러시를 호출하려면 em.flush() 메소드를 직접 사용해도 되지만 보통 플러시 모드(Flush Mode)에
	 * 따라 커밋하기 직전이나 쿼리 실행 직전에 자동으로 플러시가 호출된다.
	 * 
	 * em.setFlushMode(FlushModeType.AUTO); // 커밋 또는 쿼리 실행 시 플러스(기본 값)
	 * em.setFlushMode(FlushModeType.COMMIT); // 커밋시에만 플러시
	 * 
	 * 플러시 모드는 FlushModeType.AUTO가 기본 값이다. 따라서 JPA는 트랜잭션 커밋 직전이나 쿼리 실행 직전에
	 * 자동으로 플러시를 호출한다. 다른 옵션으로는 FlushModeType.COMMIT이 있는데 이 모드는 커밋 시에만 플러시를
	 * 호출하고 쿼리 실행 시에는 플러시를 호출하지 않는다.
	 * 이 옵션은 성능 최적화를 위해 꼭 필요할 때만 사용해야 한다.
	 * 
	 * 플러시 모드와 최적화
	 * FlushModeType.COMMIT 모드는 트랜잭션을 커밋할 때만 플러시하고 쿼리를 실행할 때는 플러시 하지 않는다.
	 * 따라서 JPA 쿼리를 사용할 대 영속성 컨텍스트에는 있지만 아직 데이터베이스에 반영하지 않은 데이터를
	 * 조회할 수 없다.
	 * 이런 상황은 잘못하면 데이터 무결성에 심각한 피해를 줄 수 있따.
	 * 그럼에도 아래와 같이 플러시가 너무 자주 일어나는 상황에 이 모드를 사용하면 쿼리시 발생하는 플러시 횟수를
	 * 줄여서 성능을 최적화할 수 있다.
	 *  
	 * 등록()
	 * 쿼리() // 플러시
	 * 등록()
	 * 쿼리() // 플러시
	 * 등록()
	 * 쿼리() // 플러시
	 * 커밋() // 플러시 
	 * ㅁFlushModeType.AUTO : 쿼리와 커밋할 때 총 4번 플러시한다.
	 * ㅁFlushModeType.COMMIT : 커밋 시에만 1번 플러시한다.
	 * 
	 * JPA를 사용하지 않고 JDBC를 직접 사용해서 SQL을 실행할 떄도 플러시 모드를 고민해야 한다.
	 * JPA를 통하지 않고 JDBC로 쿼리를 직접 실행하면 JPA는 JDBC가 실행한 쿼리를 인식할 방법이 없다.
	 * 따라서 별도의 JDBC 호출은 플러시 모드를 AUTO 설정해도 플러시가 일어나지 않는다.
	 * 이때는 JDBC로 쿼리를 실행하기 직전에 em.flush()를 호출해서 영속성 컨텍스트의 내용을 데이터베이스에
	 * 동기화하는 것이 안전하다.
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		Print subPrint = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.logic((em ,tx) -> {
				/*
				 * JPQL은 영속성 컨텍스트에 있는 데이터를 고려하지 않고 데이터베이스에서 데이터를 조회한다.
				 * 따라서 JPQL을 실행하기 전에 영속성 컨텍스트의 내용을 데이터베이스에 반영해야 한다.
				 * 그렇지 않으면 의도하지 않는 결과가 발생할 수 있다.
				 */
				print.mainStartPrint("쿼리와 플러시 모드");
				tx.begin();

				/*
				 * product.setPrice(2000)을 호출하면 영속성 컨텍스트의 상품 엔티티는 가격이 1000원에서 2000원으로
				 * 변경되지만 데이터베이스에는 1000원인 상태로 남아있다.
				 * 다음으로 JPQL을 호출해서 가격이 2000원인 상품을 조회했는데 이때 플러시 모드를 따로 설정하지 않으면
				 * AUTO이므로 쿼리 실행 직전에 영속성 컨텍스트가 플러시 된다.
				 * 따라서 방금 2000원으로 수정한 상품을 조회할 수 있다.
				 */
				subPrint.subStartPrint("쿼리와 플러시 모드 예제");
				Product product = em.find(Product.class, 1L);
				System.out.println(String.format("product name : %s, product price : %s"
					, product.getName()
					, product.getPrice() )) ;
				
				product.setPrice(2000);
				
				em.createQuery("SELECT p FROM CH10_OOQ_PRODUCT p WHERE p.price = 2000", Product.class)
					.getResultList().stream().forEach(p -> {
						System.out.println(String.format("product name : %s, product price : %s"
							, p.getName()
							, p.getPrice()) );						
					});
				subPrint.subEndPrint();
				
				tx.commit();
				print.mainEndPrint();
			})
			.start();
		
		new Logic(JPA_AUTO.UPDATE)
			.logic((em, tx) -> {
				print.mainStartPrint("쿼리와 플러시 모드");
				tx.begin();
				
				/*
				 * 플러시 모드를 COMMIT으로 설정하면 쿼리시에는 플러시 하지 않으므로 방금 수정한 데이터를
				 * 조회할 수 없다.
				 */
				subPrint.subStartPrint("플러시 모드 설정");
				// 커밋 시에만 플러시.
				em.setFlushMode(FlushModeType.COMMIT);
				
				Product product = em.find(Product.class, 1L);
				System.out.println(String.format("product name : %s, product price : %s"
					, product.getName()
					, product.getPrice() )) ;
				
				product.setPrice(5000);

				/*
				 * 아래와 같이 flush 메소드를 직접 호출하면 COMMIT모드라 하더라도 데이터베이스 반영이 된다.
				 */
				// em.flush();
				
				em.createQuery("SELECT p FROM CH10_OOQ_PRODUCT p WHERE p.price = 5000", Product.class)
					/*
					 * 위 flush 메소드 대신 아래 setFlushMode 메소드를 사용하여 해당 쿼리에만 플러시 모드를
					 * 지정할 수 있다.
					 * 이렇게 쿼리에 설정하는 플러시 모드는 엔티티 매니저에 설정하는 플러시 모드보다 우선권을 가진다. 
					 */
					//.setFlushMode(FlushModeType.AUTO)
					.getResultList().stream().forEach(p -> {
						System.out.println(String.format("product name : %s, product price : %s"
							, p.getName()
							, p.getPrice()) );						
					});
				
				subPrint.subEndPrint();
				
				tx.commit();
				print.mainEndPrint();
			})
			.start();
			

	}

}
