package model5.entity.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import model5.entity.item.mapping.Item;

@Entity(name = "CH08_MODEL5_ITEM_BOOK")
@DiscriminatorValue("B")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Book extends Item {
	
	private String author;
	
	private String isbn;
	
	public Book() { }
	
	public Book(String author, String isbn) {
		this.author = author;
		this.isbn = isbn;
	}

	public Book(String name, int price, int stockquantity, String author, String isbn) {
		this.setName(name);
		this.setPrice(price);
		this.setStockquantity(stockquantity);
		this.author = author;
		this.isbn = isbn;
	}
	
	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }

	public String getIsbn() { return isbn; }
	public void setIsbn(String isbn) { this.isbn = isbn; }
}
