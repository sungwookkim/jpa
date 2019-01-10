package model6.entity.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import model6.entity.item.mapping.Item;

@Entity(name = "CH09_MODEL6_ITEM_ALBUM")
@DiscriminatorValue("A")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Album extends Item {

	private String artist;
	
	private String etc;
	
	public Album() { }
	
	public Album(String artist, String etc) {
		this.artist = artist;
		this.etc = etc;
	}

	public Album(String name, int price, int stockquantity, String artist, String etc) {
		this.setName(name);
		this.setPrice(price);
		this.setStockquantity(stockquantity);
		this.artist = artist;
		this.etc = etc;
	}
	
	public String getArtist() { return artist; }
	public void setArtist(String artist) { this.artist = artist; }

	public String getEtc() { return etc; }
	public void setEtc(String etc) { this.etc = etc; }
}
