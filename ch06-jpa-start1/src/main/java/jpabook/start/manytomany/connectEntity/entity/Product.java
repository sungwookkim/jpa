package jpabook.start.manytomany.connectEntity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@SequenceGenerator(
	name = "CH06_MANY_TO_MANY_CONNECT_PRODUCT_SEQ_GENRATOR"
	, sequenceName = "CH06_MANY_TO_MANY_CONNECT_PRODUCT_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@Entity(name = "CH06_MANY_TO_MANY_CONNECT_PRODUCT")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH06_MANY_TO_MANY_CONNECT_PRODUCT_SEQ")
	@Column(name = "PRODUCT_ID")
	private long id;
	
	private String name;
	
	public Product() { }
	
	public Product(String name) { 
		this.name = name;
	}
	
	public long getId() { return this.id; }
	
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }
}
