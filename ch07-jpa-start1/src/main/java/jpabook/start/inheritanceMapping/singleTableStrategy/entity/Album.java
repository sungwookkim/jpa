package jpabook.start.inheritanceMapping.singleTableStrategy.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import jpabook.start.inheritanceMapping.singleTableStrategy.entity.abs.Item;

@Entity(name = "CH07_SINGLETABLE_STRATEGY_ALBUM")
// 엔티티를 저장할 때 구분 컬럼에 입력할 값을 지정한다.
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
