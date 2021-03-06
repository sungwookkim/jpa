package jpabook.start.inheritanceMapping.joinedStrategy.entity;

import jpabook.start.inheritanceMapping.joinedStrategy.entity.abs.Item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity(name = "CH07_JOINED_STRATEGY_BOOK")
// 엔티티를 저장할 때 구분 컬럼에 입력할 값을 지정한다.
@DiscriminatorValue("B")
// 자식 테이블의 기본 컬럼명을 변경하고 싶으면 @PrimaryKeyJoinColumn을 사용한다.
@PrimaryKeyJoinColumn(name = "BOOK_ID")
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
