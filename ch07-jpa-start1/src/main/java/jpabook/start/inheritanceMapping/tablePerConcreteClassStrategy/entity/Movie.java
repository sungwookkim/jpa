package jpabook.start.inheritanceMapping.tablePerConcreteClassStrategy.entity;

import javax.persistence.Entity;

import jpabook.start.inheritanceMapping.tablePerConcreteClassStrategy.entity.abs.Item;

@Entity(name = "CH07_TABLE_PER_CONCRETE_CLASS_STRATEGY_MOVIE")
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
