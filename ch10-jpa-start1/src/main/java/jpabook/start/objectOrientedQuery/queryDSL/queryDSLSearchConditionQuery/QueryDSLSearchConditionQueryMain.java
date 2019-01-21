package jpabook.start.objectOrientedQuery.queryDSL.queryDSLSearchConditionQuery;

import java.util.List;

import javax.persistence.EntityManager;

import com.querydsl.jpa.impl.JPAQuery;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Product;
import jpabook.start.objectOrientedQuery.entity.QProduct;

public class QueryDSLSearchConditionQueryMain extends DataInit {

	/*
	 * 검색 조건 쿼리
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("검색 조건 쿼리");
				JPAQuery<EntityManager> query = new JPAQuery<>(em);
				QProduct product = QProduct.product;
				
				List<Product> products= query.select(product)
					.from(product)
					/*
					 * where 절에는 and나 or을 사용할 수 있다.
					 * 쿼리 타입의 필드는 필요한 대부분의 메소드를 명시적으로 제공한다.
					 * 몇 가지만 예를 들어보자. 다음은 where()에서 사용되는 메소드다.
					 * 
					 * product.price.between(10000, 20000); // 가격이 10000원 ~ 20000원 상품
					 * product.name.contains("상품1"); // 상품1이라는 이름을 포함한 상품, SQL에서 like '%상품1%' 검색
					 * product.name.startsWith("고급"); // 이름이 고급으로 시작하는 상품, SQL에서 like '고급%' 검색
					 */
					.where(product.name.eq("computer").and(product.price.gt(900)) )
					.fetch();
				
				products.stream().forEach(p -> {
					System.out.println(String.format("product id : %s, product name : %s, product price : %s"
						, p.getId()
						, p.getName()
						, p.getPrice() ));
				});
				print.mainEndPrint();
			})
			.start();
		

	}

}
