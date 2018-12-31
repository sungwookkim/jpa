package jpabook.start.mappedSuperClass.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import jpabook.start.mappedSuperClass.entity.abs.BaseEntity;

@Entity(name = "CH07_MAPPED_SUPER_CLASS_SELLER")
@SequenceGenerator(
	name = "CH07_MAPPED_SUPER_CLASS_SEQUENCE"
	, sequenceName = "CH07_MAPPED_SUPER_CLASS_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
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
