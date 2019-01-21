package jpabook.start.objectOrientedQuery.queryDSL.queryDSLDynamicQuery;

import java.util.Optional;
import java.util.regex.Pattern;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;

import common.util.JPA_AUTO;
import common.util.Logic;
import common.util.Print;
import jpabook.start.objectOrientedQuery.DataInit;
import jpabook.start.objectOrientedQuery.entity.QProduct;

public class QueryDSLDynamicQueryMain extends DataInit {

	/*
	 * 동적 쿼리
	 * 
	 * com.querydsl.core.BooleanBuilder를 사용하면 특정 조건에 따른 동적 쿼리를 편리하게 생성할 수 있다.
	 */
	public static void main(String[] args) {
		initSave();
		
		Print print = new Print();
		new Logic(JPA_AUTO.UPDATE)
			.commitAfter(em -> {
				print.mainStartPrint("동적 쿼리");
				SearchParam searchParam = new SearchParam();
				searchParam.setName("computer");
				searchParam.setPrice(900);
				
				QProduct product = QProduct.product;				
				BooleanBuilder booleanBuilder = new BooleanBuilder();

				Optional.ofNullable(searchParam.getName())
					.filter(n -> n.length() > 0 )
					.filter(n -> !Pattern.compile("\\s").matcher(n).find())
					.ifPresent(n -> {
						booleanBuilder.and(product.name.contains(n));
					});
				
				Optional.ofNullable(searchParam.getPrice())
					.ifPresent(p -> {
						booleanBuilder.and(product.price.gt(p));
					});

				new JPAQuery<>(em)
					.select(product)
					.from(product)
					.where(booleanBuilder)
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
