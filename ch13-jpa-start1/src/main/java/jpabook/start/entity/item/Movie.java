package jpabook.start.entity.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jpabook.start.entity.item.mapping.Item;

@Entity(name = "CH14_ITEM_MOVIE")
@DiscriminatorValue("M")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Movie extends Item {

	private String director;
	
	private String actor;
	
	public Movie() { }
	
	public Movie(String director, String actor) {
		this.director = director;
		this.actor = actor;
	}
	
	public Movie(String name, int price, int stockquantity, String director, String actor) {
		this.setName(name);
		this.setPrice(price);
		this.setStockquantity(stockquantity);
		this.director = director;
		this.actor = actor;
	}

	public String getDirector() { return director; }
	public void setDirector(String director) { this.director = director; }

	public String getActor() { return actor; }
	public void setActor(String actor) { this.actor = actor; }
}

