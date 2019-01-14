package jpabook.start.objectOrientedQuery.jpql.entity.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import jpabook.start.objectOrientedQuery.jpql.entity.item.abs.Item;


@Entity(name = "CH10_ALBUM")
@DiscriminatorValue("A")
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
