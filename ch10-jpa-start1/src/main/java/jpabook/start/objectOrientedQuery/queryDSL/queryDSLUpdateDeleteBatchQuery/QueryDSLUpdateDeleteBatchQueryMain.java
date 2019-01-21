package jpabook.start.objectOrientedQuery.queryDSL.queryDSLUpdateDeleteBatchQuery;

import java.util.Optional;

import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAUpdateClause;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.QProduct;

public class QueryDSLUpdateDeleteBatchQueryMain extends DataInit {

	/*
	 * 수정, 삭제 배치 쿼리
	 * 
	 * QueryDSL도 수정, 삭제 같은 배치 쿼리를 지원한다. JPQL 배치 쿼리와 같이 영속성 컨텍스트를
	 * 무시하고 데이터베이스를 직접 쿼리한다는 점에 유의하자.
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		Print subPrint = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.logic((em, tx) -> {
				tx.begin();
				print.mainStartPrint("수정, 삭제 배치 쿼리");

				subPrint.subStartPrint("수정 배치 쿼리");
				QProduct product = QProduct.product;
				
				JPAUpdateClause updateClause = new JPAUpdateClause(em, product);
				long count = updateClause
					.where(product.name.eq("computer"))
					.set(product.stockAmount, product.stockAmount.add(100))
					.execute();
				
				System.out.println(String.format("--> updateClause count : %s", count));

				new JPAQuery<>(em)
					.select(product)
					.from(product)
					.where(product.name.eq("computer"))
					.fetch().stream().forEach(q -> {
						System.out.println(String.format("--> update %s stockAmount : %s"
							, q.getName()
							, q.getStockAmount() ));
					});
				subPrint.subEndPrint();



				subPrint.subStartPrint("삭제 배치 쿼리");
				JPADeleteClause jpaDeleteClause = new JPADeleteClause(em, product);
				count = jpaDeleteClause.where(product.name.eq("computer"))
					.execute();
				
				System.out.println(String.format("--> deleteClause count : %s", count));

				Optional.ofNullable(new JPAQuery<>(em)
					.select(product)
					.from(product)
					.where(product.name.eq("computer"))
					.fetchResults().getTotal() )
				.filter(t -> t > 0)
				.ifPresent(t -> {
					System.out.println(String.format("--> delete count : %s", t));
				});
				subPrint.subEndPrint();
				
				print.mainEndPrint();
				tx.commit();
			})
			.start();

	}

}
