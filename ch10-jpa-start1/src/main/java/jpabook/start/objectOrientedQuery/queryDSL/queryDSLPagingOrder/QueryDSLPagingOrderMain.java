package jpabook.start.objectOrientedQuery.queryDSL.queryDSLPagingOrder;

import java.util.List;

import javax.persistence.EntityManager;

import com.querydsl.core.QueryModifiers;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Product;
import jpabook.start.objectOrientedQuery.entity.QProduct;

public class QueryDSLPagingOrderMain extends DataInit {

	/*
	 * 페이징과 정렬
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		Print subPrint = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("페이징과 정렬");
				
				/*
				 * 정렬은 orderBy를 사용하는데 쿼리타입(Q)이 제공하는 asc(), desc()를 사용한다.
				 * 페이징은 offset과 limit를 적절히 조합해서 사용하면 된다.
				 */
				subPrint.subStartPrint("페이징과 정렬");
				JPAQuery<EntityManager> query =  new JPAQuery<>(em);
				QProduct product = QProduct.product;
				
				List<Product> products = query.select(product)
					.from(product)
					.where(product.price.gt(900))
					.orderBy(product.price.desc(), product.stockAmount.asc())
					.offset(2).limit(2)
					.fetch();
	
				products.stream().forEach(p -> {
					System.out.println(String.format("product id : %s, product name : %s, product price : %s"
						, p.getId()
						, p.getName()
						, p.getPrice() ));
				});
				subPrint.subEndPrint();

				

				subPrint.subStartPrint("페이징과 정렬 QueryModifiers 사용");
				QueryModifiers queryModifiers = new QueryModifiers(1L, 2L);
				query =  new JPAQuery<>(em);

				products = query.select(product)
					.from(product)
					.where(product.price.gt(900))
					.orderBy(product.price.desc(), product.stockAmount.asc())
					.restrict(queryModifiers)
					.fetch();

				products.stream().forEach(p -> {
					System.out.println(String.format("product id : %s, product name : %s, product price : %s"
						, p.getId()
						, p.getName()
						, p.getPrice() ));
				});
				subPrint.subEndPrint();
				
				
				
				subPrint.subStartPrint("페이징과 정렬 QueryResults() 사용");
				query =  new JPAQuery<>(em);

				QueryResults<Product> queryResults = query.select(product)
					.from(product)
					.where(product.price.gt(900))
					.orderBy(product.price.desc(), product.stockAmount.asc())
					.offset(1).limit(3)
					.fetchResults();

				System.out.println(String.format("total : %s, offset : %s, limit : %s"
					, queryResults.getTotal()
					, queryResults.getOffset()
					, queryResults.getLimit() ));

				queryResults.getResults().stream().forEach(p -> {
					System.out.println(String.format("product id : %s, product name : %s, product price : %s"
						, p.getId()
						, p.getName()
						, p.getPrice() ));
				});
				subPrint.subEndPrint();
				
				print.mainEndPrint();
			})
			.start();

	}

}
