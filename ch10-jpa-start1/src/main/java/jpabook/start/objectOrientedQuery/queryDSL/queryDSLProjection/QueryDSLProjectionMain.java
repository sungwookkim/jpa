package jpabook.start.objectOrientedQuery.queryDSL.queryDSLProjection;

import javax.persistence.EntityManager;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.QProduct;

public class QueryDSLProjectionMain extends DataInit {

	/*
	 * 프로젝션과 결과 반환
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		Print subPrint = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("프로젝션과 결과 반환");
				
				subPrint.subStartPrint("프로젝션 대상이 하나");
				JPAQuery<EntityManager> query = new JPAQuery<>(em);
				QProduct product = QProduct.product;
				
				query.select(product)
					.from(product)
					.fetch().stream().forEach(q -> {
						System.out.println(String.format("product id : %s, product name : %s, product price : %s, product stockAmount : %s"
							, q.getId()
							, q.getName()
							, q.getPrice()
							, q.getStockAmount() ));
					});
				subPrint.subEndPrint();
				
				
				/*
				 * 프로젝션 대상으로 여러 필드를 선택하면 QueryDSL은 기본으로 com.querydsl.core.Tuple
				 * 이라는 Map과 비슷한 내부 타입을 사용한다.
				 * 조회 결과는 tuple.get() 메소드에 조회한 쿼리 타입을 지정하면 된다.
				 * 
				 */
				subPrint.subStartPrint("여러 컬럼 반환과 튜플");
				query = new JPAQuery<>(em);
				product = QProduct.product;
				
				query.select(product.stockAmount, product.name)
					.from(product)
					.fetch().stream().forEach(q -> {
						String n = q.get(QProduct.product.name);
						int s = q.get(QProduct.product.stockAmount);
						
						System.out.println(String.format("product name : %s, product stockAmount : %s"
							, n, s));
					});
				subPrint.subEndPrint();
				
				
				
				/*
				 * 빈 생성
				 * 쿼리 결과를 엔티티가 아닌 특정 객체로 받고 싶으면 빈 생성(Bean population)  기능을
				 * 사용한다. QueryDSL은 객체를 생성하는 다양한 방법을 제공한다.
				 * 	ㅁ 프로퍼티 접근
				 * 	ㅁ 필드 직접 접근
				 * 	ㅁ 생성자 사용
				 */
				subPrint.subStartPrint("빈 생성");
				query = new JPAQuery<>(em);
				product = QProduct.product;
				
				/*
				 * Projections.bean() 메소드는 수정자(setter)를 사용해서 값을 채운다.
				 * 예제를 보면 쿼리 결과는 name인데 ProductDTO는 productName 프로퍼티를 가지고 있다.
				 * 이런 경우에는 as를 사용해서 별칭을 주면 된다.
				 */
				query.select(Projections.bean(ProductDTO.class
						, product.name.as("productName")
						, product.stockAmount.as("stockAmount") ))
					.from(product).fetch().stream().forEach(p -> {
						System.out.println(String.format("product name : %s, product stockAmount : %s"
							, p.getProductName()
							, p.getStockAmount() ));
					});

				/*
				 * Projections.fields 메소드를 사용하면 필드에 직접 접근해서 값을 채워준다.
				 */
				query.select(Projections.fields(ProductDTO.class, product.name.as("productName")))
					.from(product).fetch().stream().forEach(p -> {
						System.out.println(String.format("product name : %s"
							, p.getProductName()));
					});
				
				/*
				 * Projections.constructor 메소드는 생성자를 사용한다.
				 * 물론 지정한 프로젝션과 파라미터 순서가 같은 생성자가 필요하다.
				 */
				query.select(Projections.constructor(ProductDTO.class, product.name, product.stockAmount))
					.from(product).fetch().stream().forEach(p -> {
						System.out.println(String.format("product name : %s, product stockAmount : %s"
							, p.getProductName()
							, p.getStockAmount() ));						
					});
				subPrint.subEndPrint();
				
				print.mainEndPrint();
			})
			.start();
	}

}
