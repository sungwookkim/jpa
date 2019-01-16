package jpabook.start.objectOrientedQuery.entity.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import jpabook.start.objectOrientedQuery.entity.item.abs.Item;

@Entity(name = "CH10_MOVIE") 
@DiscriminatorValue("M")
public class Movie extends Item {

	private String director;
	private String actor;

	public Movie() { }
	
	public Movie(String director, String actor, String name, int price) {
		this.director = director;
		this.actor = actor;
		this.setName(name);
		this.setPrice(price);
	}
	
	public String getDirector() { return director; }
	public void setDirector(String director) { this.director = director; }

	public String getActor() { return actor; }
	public void setActor(String actor) { this.actor = actor; }
}
