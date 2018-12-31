package jpabook.start.mappedSuperClass.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

import jpabook.start.mappedSuperClass.entity.abs.BaseEntity;

@Entity(name = "CH07_MAPPED_SUPER_CLASS_SELLER")
@AttributeOverride(name = "id", column = @Column(name = "SELLER_ID"))
public class Seller extends BaseEntity {
	
	private String shopName;
	
	public Seller() { }
	
	public Seller(String name, String shopName) {
		this.setName(name);
		this.shopName = shopName;
	}
	
	public String getShopName() { return this.shopName; }
	public void setShopName(String shopName) { this.shopName = shopName; }
}
