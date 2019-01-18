package jpabook.start.objectOrientedQuery.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Member.class)
public abstract class Member_ {

	public static volatile SingularAttribute<Member, Long> id;
	public static volatile SingularAttribute<Member, Team> team;
	public static volatile SingularAttribute<Member, String> userName;
	public static volatile SingularAttribute<Member, Integer> age;
	public static volatile ListAttribute<Member, Order> order;

	public static final String ID = "id";
	public static final String TEAM = "team";
	public static final String USER_NAME = "userName";
	public static final String AGE = "age";
	public static final String ORDER = "order";

}

