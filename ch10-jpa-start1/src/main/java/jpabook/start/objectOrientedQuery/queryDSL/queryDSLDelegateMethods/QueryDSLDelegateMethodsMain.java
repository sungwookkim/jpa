package jpabook.start.objectOrientedQuery.queryDSL.queryDSLDelegateMethods;

import com.querydsl.jpa.impl.JPAQuery;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.QProduct;

public class QueryDSLDelegateMethodsMain extends DataInit {

	/*
	 * 메소드 위임
	 * 
	 * com.querydsl.core.annotations.QueryDelegate 어노테이션에 속성으로 기능을 적용할
	 * 엔티티를 지정한다. 정적 메소드의 첫 번째 파라미터에는 대상 엔티티의 쿼리 타입(Q)을
	 * 지정하고 나머지는 필요한 파라미터를 정의한다.
	 * 
	 * ProductExpression 클래스를 참고 하면 된다.
	 */
	public static void main(String[] args) {
		initSave();

		Print print = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("메소드 위임");
				QProduct product = QProduct.product;
				
				new JPAQuery<>(em).select(product)
					.from(product)
					// isExpensive 메소드가 메소드 위임을 사용하였다.
					.where(product.isExpensive(1000))
					.fetch().stream().forEach(p -> {
						System.out.println(String.format("product id : %s, product name : %s, product price : %s, product stockAmount : %s"
							, p.getId()
							, p.getName()
							, p.getPrice()
							, p.getStockAmount() ));
					});
				print.mainEndPrint();
			})
			.start();
	}
}
