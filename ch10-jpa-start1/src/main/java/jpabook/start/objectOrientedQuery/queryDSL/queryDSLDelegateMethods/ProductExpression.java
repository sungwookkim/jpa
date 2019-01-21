package jpabook.start.objectOrientedQuery.queryDSL.queryDSLDelegateMethods;

import com.querydsl.core.annotations.QueryDelegate;
import com.querydsl.core.types.dsl.BooleanExpression;

import jpabook.start.objectOrientedQuery.entity.Product;
import jpabook.start.objectOrientedQuery.entity.QProduct;

public class ProductExpression {

	@QueryDelegate(Product.class)
	public static BooleanExpression isExpensive(QProduct qProduct, Integer price) {
		return qProduct.price.gt(price);
	}
}
