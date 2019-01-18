package jpabook.start.objectOrientedQuery.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ {

	public static volatile SingularAttribute<Product, Integer> stockAmount;
	public static volatile SingularAttribute<Product, Integer> price;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, Long> id;
	public static volatile ListAttribute<Product, Order> order;

	public static final String STOCK_AMOUNT = "stockAmount";
	public static final String PRICE = "price";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String ORDER = "order";

}

