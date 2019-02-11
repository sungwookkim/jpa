package domain.entity.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import domain.entity.item.abs.Item;

@Entity(name = "CH11_ALBUM")
@DiscriminatorValue("A")
public class Album extends Item {

	private String artist;
	
	private String etc;
	
	public Album() {}
	public Album(String artist, String etc) {
		this.artist = artist;
		this.etc = etc;
	}
	
	public Album(String name, int price, int stockQuantity, String artist, String etc) {
		this.setName(name);
		this.setPrice(price);
		this.setStockQuantity(stockQuantity);
		this.artist = artist;
		this.etc = etc;
	}
	
	public String getArtist() { return artist; }
	public void setArtist(String artist) { this.artist = artist; }
	
	public String getEtc() { return etc; }
	public void setEtc(String etc) { this.etc = etc; }
}
