package domain.entity.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import domain.entity.item.abs.Item;

@Entity(name = "CH11_BOOK")
@DiscriminatorValue("B")
public class Book extends Item {

	private String author;
	
	private String isbn;
	
	public Book() {}
	public Book(String author, String isbn) {
		this.author = author;
		this.isbn = isbn;
	}
	
	public Book(String name, int price, int stockQuantity, String author, String isbn) {
		this.setName(name);
		this.setPrice(price);
		this.setStockQuantity(stockQuantity);
		this.author = author;
		this.isbn = isbn;
	}
	
	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }
	
	public String getIsbn() { return isbn; }
	public void setIsbn(String isbn) { this.isbn = isbn; }
}
