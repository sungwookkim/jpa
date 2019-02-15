package domain.entity.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import domain.entity.item.abs.Item;

@Entity(name = "CH15_MOVIE")
@DiscriminatorValue("M")
public class Movie extends Item {

	private String director;
	
	private String actor;
	
	public Movie() {  }
	public Movie(String director, String actor) {
		this.director = director;
		this.actor = actor;
	}
	
	public Movie(String name, int price, int stockQuantity, String director, String actor) {
		this.setName(name);
		this.setPrice(price);
		this.setStockQuantity(stockQuantity);
		this.director = director;
		this.actor = actor;
	}
	
	public String getDirector() { return director; }
	public void setDirector(String director) { this.director = director; }
	
	public String getActor() { return actor; }
	public void setActor(String actor) { this.actor = actor; }
}
