package jpabook.start.inheritanceMapping.tablePerConcreteClassStrategy.entity;

import javax.persistence.Entity;

import jpabook.start.inheritanceMapping.tablePerConcreteClassStrategy.entity.abs.Item;

@Entity(name = "CH07_TABLE_PER_CONCRETE_CLASS_STRATEGY_ALBUM")
public class Album extends Item {

	private String artist;
	
	public Album() { }
	
	public Album(String artist, String name, int price) {
		this.artist = artist;
		this.setName(name);
		this.setPrice(price);
	}

	public String getArtist() { return artist; }
	public void setArtist(String artist) { this.artist = artist; }
}
