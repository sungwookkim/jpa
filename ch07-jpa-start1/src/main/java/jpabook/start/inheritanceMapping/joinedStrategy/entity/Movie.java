package jpabook.start.inheritanceMapping.joinedStrategy.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import jpabook.start.inheritanceMapping.joinedStrategy.entity.abs.Item;

@Entity(name = "CH07_JOINED_STRATEGY_MOVIE")
// 엔티티를 저장할 때 구분 컬럼에 입력할 값을 지정한다.
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
