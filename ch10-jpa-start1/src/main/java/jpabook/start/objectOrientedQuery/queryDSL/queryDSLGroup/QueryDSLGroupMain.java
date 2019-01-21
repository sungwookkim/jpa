package jpabook.start.objectOrientedQuery.queryDSL.queryDSLGroup;

import java.util.List;

import javax.persistence.EntityManager;

import com.querydsl.jpa.impl.JPAQuery;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.Product;
import jpabook.start.objectOrientedQuery.entity.QProduct;

public class QueryDSLGroupMain extends DataInit {

	/*
	 * 그룹 
	 * 
	 * groupBy를 사용하고 그룹화된 결과를 제한하려면 having을 사용하면 된다.
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("groupBy() 사용");
				JPAQuery<EntityManager> query = new JPAQuery<>(em);
				QProduct product = QProduct.product;
				
				List<Product> products = query.select(product)
					.from(product)
					.groupBy(product.price)
					.having(product.price.gt(2000))
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
