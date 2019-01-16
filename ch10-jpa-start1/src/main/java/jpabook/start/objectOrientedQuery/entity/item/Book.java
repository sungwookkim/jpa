package jpabook.start.objectOrientedQuery.entity.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import jpabook.start.objectOrientedQuery.entity.item.abs.Item;

@Entity(name = "CH10_BOOK")
// 엔티티를 저장할 때 구분 컬럼에 입력할 값을 지정한다.
@DiscriminatorValue("B")
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
