package jpabook.start.objectOrientedQuery.objectOrientedDeepening.bulkOperation;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Product;

public class BulkOperationMain extends DataInit {

	/*
	 * 벌크 연산
	 * 엔티티를 수정하려면 영속성 컨텍스트의 변경 감지 기능이나 병합을 사용하고,
	 * 삭제하려면 EntityManager.remove() 메소드를 사용한다.
	 * 하지만 이 방법으로 수백 개 이상의 엔티티를 하나씩 처리하기에는 시간이 너무 오래 걸린다.
	 * 이럴 때 여러 건을 한 번에 수정하거나 삭제하는 벌크 연산을 사용하면 된다.
	 * 
	 * 참고
	 * JPA 표준은 아니지만 하이버네이트는 INSERT 벌크 연산도 지원한다.
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		Print subPrint = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
		.logic((em, tx) -> {
			print.mainStartPrint("벌크 연산");
			tx.begin();

			String sqlString = "UPDATE CH10_OOQ_PRODUCT p"
					+ " SET p.price = p.price * 1.1"
					+ " WHERE p.stockAmount > :stockAmount";

			int resultCnt = em.createQuery(sqlString)
				.setParameter("stockAmount", 10)
				/*
				 * 벌크 연산은 executeUpdate() 메소드를 사용한다. 이 메소드는 벌크 연산으로 영향을 받은
				 * 엔티티 건수를 반환한다.
				 */
				.executeUpdate();
			System.out.println(String.format("벌크 연산 업데이트 : %s", resultCnt));
			
			/* 
			sqlString = "DELETE FROM CH10_OOQ_PRODUCT p"
				+ " WHERE p.price > :price";
			
			resultCnt = em.createQuery(sqlString)
				.setParameter("price", 1000)
				.executeUpdate();
			System.out.println(String.format("벌크 연산 삭제 : %s", resultCnt));
			*/

			
			/*
			 * 벌크 연산의 주의점
			 * 아래 코드를 보면 computer를 조회했으므로 영속성 컨텍스트에 관리된다.
			 * 그러나 벌크 연산은 영속성 컨텍스트를 통하지 않고 데이터베이스에 직접 쿼리 하기 때문에
			 * 영속성 컨텍스트에 있는 데이터와 데이터베이스에 있는 데이터가 다를 수 있다.
			 * 따라서 벌크 연산은 주의해서 사용해야 한다.
			 */
			subPrint.subStartPrint("벌크 연산의 주의점");
			// computer 상품 조회 가격은 1000원이다.
			Product product = em.createQuery("SELECT p FROM CH10_OOQ_PRODUCT p WHERE p.name = :name", Product.class)
				.setParameter("name", "computer")
				.getResultList().stream().findAny().orElseGet(Product::new);
			System.out.println(String.format("---> product 수정 전 = %s", product.getPrice()));
			
			// 벌크 연산 수행으로 모든 상품 가격 10% 상승
			resultCnt = em.createQuery("UPDATE CH10_OOQ_PRODUCT p SET p.price = p.price * 1.1").executeUpdate();
			System.out.println(String.format("---> 벌크 연산 결과 = %s", resultCnt));
			
			// 그럼에도 불과하고 가격은 그대로 1000원이다.
			System.out.println(String.format("---> product 수정 후 = %s", product.getPrice()));
			subPrint.subEndPrint();

			tx.commit();
			print.mainEndPrint();
		})
		.start();
		
		/*
		 * 벌크 연산 문제해결
		 * 
		 * em.refresh() 사용
		 * 벌크 연산을 수행한 직후에 정확한 상품 엔티티를 사용해야 한다면 em.refresh()를 사용해서 데이터베이스에서
		 * 상품를 다시 조회한다.
		 */
		new Logic(JPA_AUTO.UPDATE)
			.logic((em, tx) -> {
				print.mainStartPrint("[1] 벌크 연산 문제해결");
				tx.begin();

				Product product = em.createQuery("SELECT p FROM CH10_OOQ_PRODUCT p WHERE p.name = :name", Product.class)
					.setParameter("name", "computer")
					.getResultList().stream().findAny().orElseGet(Product::new);
				System.out.println(String.format("---> product 수정 전 = %s", product.getPrice()));
				
				int resultCnt = em.createQuery("UPDATE CH10_OOQ_PRODUCT p SET p.price = p.price * 1.1").executeUpdate();
				System.out.println(String.format("---> 벌크 연산 결과 = %s", resultCnt));
				
				// em.refresh 사용
				em.refresh(product);
				
				System.out.println(String.format("---> product 수정 후 = %s", product.getPrice()));				
				
				tx.commit();
				print.mainEndPrint();
			}).start();

		
		/*
		 * 벌크 연산 문제해결
		 * 
		 * 벌크 연산을 먼저 실행
		 * 가장 실용적인 해결책은 벌크 연산을 가장 먼저 실행하는 것이다.
		 * 벌크 연산을 먼저 실행하고 나서 상품를 조회하면 연산으로 이미 변경된 상품을 조회하게 된다.
		 * 이 방법은 JPA와 JDBC를 함게 사용할 때도 유용하다.
		 */
		new Logic(JPA_AUTO.UPDATE)
			.logic((em, tx) -> {
				print.mainStartPrint("[2] 벌크 연산 문제해결");
				tx.begin();

				int resultCnt = em.createQuery("UPDATE CH10_OOQ_PRODUCT p SET p.price = p.price * 1.1").executeUpdate();
				System.out.println(String.format("---> 벌크 연산 결과 = %s", resultCnt));

				Product product = em.createQuery("SELECT p FROM CH10_OOQ_PRODUCT p WHERE p.name = :name", Product.class)
					.setParameter("name", "computer")
					.getResultList().stream().findAny().orElseGet(Product::new);
				System.out.println(String.format("---> product 조회 = %s", product.getPrice()));
				
				tx.commit();
				print.mainEndPrint();
			})
			.start();
		
		/*
		 * 벌크 연산 문제해결
		 * 
		 * 벌크 연산 수행 후 영속성 컨텍스트 초기화
		 * 벌크연산을 수행한 직후에 바료 영속성 컨텍스트를 초기화해서 영속성 컨텍스트에 남아 있는 엔티티를 제거하는 것도
		 * 좋은 방법이다.
		 * 그렇지 않으면 엔티티를 조회할 때 영속성 컨텍스트에 남아 있는 엔티티를 조회할 수 있는데 이 엔티티에는 벌크 연산이
		 * 적용되어 있지 않다. 영속성 컨텍스트를 초기화하면 이후 엔티티를 조회할 때 벌크 연산이 적용된 데이터베이스에서
		 * 엔티티를 조회한다.
		 */
		new Logic(JPA_AUTO.UPDATE)
			.logic((em, tx) -> {
				print.mainStartPrint("[3] 벌크 연산 문제해결");
				tx.begin();
				
				Product product = em.createQuery("SELECT p FROM CH10_OOQ_PRODUCT p WHERE p.name = :name", Product.class)
						.setParameter("name", "computer")
						.getResultList().stream().findAny().orElseGet(Product::new);
				System.out.println(String.format("---> product 수정 전 = %s", product.getPrice()));
				
				int resultCnt = em.createQuery("UPDATE CH10_OOQ_PRODUCT p SET p.price = p.price * 1.1").executeUpdate();
				System.out.println(String.format("---> 벌크 연산 결과 = %s", resultCnt));
				
				// em.clear 사용
				em.clear();
				product = em.createQuery("SELECT p FROM CH10_OOQ_PRODUCT p WHERE p.name = :name", Product.class)
					.setParameter("name", "computer")
					.getResultList().stream().findAny().orElseGet(Product::new);
				
				System.out.println(String.format("---> product 수정 후 = %s", product.getPrice()));
				
				tx.commit();
				print.mainEndPrint();
			})
			.start();		
	}

}
