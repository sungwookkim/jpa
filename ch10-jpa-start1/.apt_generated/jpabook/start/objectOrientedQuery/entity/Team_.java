package jpabook.start.objectOrientedQuery.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Team.class)
public abstract class Team_ {

	public static volatile ListAttribute<Team, Member> member;
	public static volatile SingularAttribute<Team, String> name;
	public static volatile SingularAttribute<Team, Long> id;

	public static final String MEMBER = "member";
	public static final String NAME = "name";
	public static final String ID = "id";

}

