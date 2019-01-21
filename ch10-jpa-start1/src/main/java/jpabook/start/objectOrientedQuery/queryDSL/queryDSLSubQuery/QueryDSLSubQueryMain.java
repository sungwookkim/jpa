package jpabook.start.objectOrientedQuery.queryDSL.queryDSLSubQuery;

import java.util.List;

import javax.persistence.EntityManager;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Product;
import jpabook.start.objectOrientedQuery.entity.QProduct;

public class QueryDSLSubQueryMain extends DataInit {

	/*
	 * 서브 쿼리
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		Print subPrint = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("서브 쿼리");
		
				subPrint.subStartPrint("서브 쿼리 예제 - 한 건");
				JPAQuery<EntityManager> query = new JPAQuery<>(em);
				QProduct product = QProduct.product;
				QProduct subProduct = new QProduct("subProduct");
				
				List<Product> products = query.select(product)
					.from(product)
					.where(product.stockAmount.eq(
						JPAExpressions.select(subProduct.stockAmount.max())
							.from(subProduct)
					)).fetch();

				products.stream().forEach(p -> {
					System.out.println(String.format("product id : %s, product name : %s, product stockAmount : %s"
						, p.getId()
						, p.getName()
						, p.getStockAmount() ));
				});
				subPrint.subEndPrint();
				
				
				
				subPrint.subStartPrint("서브 쿼리 예제 - 여러 건");
				query = new JPAQuery<>(em);
				product = QProduct.product;
				subProduct = new QProduct("subProduct");
				
				products = query.select(product)
					.from(product)
					.where(product.in(
						JPAExpressions.select(subProduct)
							.from(subProduct)
							.where(product.name.eq(subProduct.name))
					)).fetch();

				products.stream().forEach(p -> {
					System.out.println(String.format("product id : %s, product name : %s, product stockAmount : %s"
						, p.getId()
						, p.getName()
						, p.getStockAmount() ));
				});
				subPrint.subEndPrint();
				
				print.mainEndPrint();
			})
			.start();
			

	}

}
