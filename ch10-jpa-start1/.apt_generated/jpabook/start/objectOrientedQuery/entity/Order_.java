package jpabook.start.objectOrientedQuery.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpabook.start.objectOrientedQuery.entity.embedded.Address;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, Product> product;
	public static volatile SingularAttribute<Order, Address> address;
	public static volatile SingularAttribute<Order, Integer> orderAmount;
	public static volatile SingularAttribute<Order, Member> member;
	public static volatile SingularAttribute<Order, Long> id;

	public static final String PRODUCT = "product";
	public static final String ADDRESS = "address";
	public static final String ORDER_AMOUNT = "orderAmount";
	public static final String MEMBER = "member";
	public static final String ID = "id";

}

