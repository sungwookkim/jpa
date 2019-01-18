package jpabook.start.objectOrientedQuery.entity.item;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Book.class)
public abstract class Book_ extends jpabook.start.objectOrientedQuery.entity.item.abs.Item_ {

	public static volatile SingularAttribute<Book, String> author;
	public static volatile SingularAttribute<Book, String> isbn;

	public static final String AUTHOR = "author";
	public static final String ISBN = "isbn";

}

