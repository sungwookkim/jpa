package jpabook.start.inheritanceMapping.tablePerConcreteClassStrategy.entity;

import javax.persistence.Entity;

import jpabook.start.inheritanceMapping.tablePerConcreteClassStrategy.entity.abs.Item;

@Entity(name = "CH07_TABLE_PER_CONCRETE_CLASS_STRATEGY_BOOK")
public class Book extends Item {

	private String author;
	private String isbn;
	
	public Book() { }
	
	public Book(String author, String isbn, String name, int price) {
		this.author = author;
		this.isbn = isbn;
		this.setName(name);
		this.setPrice(price);
	}

	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }

	public String getIsbn() { return isbn; }
	public void setIsbn(String isbn) { this.isbn = isbn; }
}
