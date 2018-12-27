package model3.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name = "CH06_MODEL3_CATEGORY_ITEM")
@SequenceGenerator(
	name = "CH06_MODEL3_CATEGORY_ITEM_SEQUENCE"
	, sequenceName = "CH06_MODEL3_CATEGORY_ITEM_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class CategoryItem {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH06_MODEL3_CATEGORY_ITEM_SEQ")
	@Column(name = "CATEGORY_ITEM_ID")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "ITEM_ID")
	private Item item;
	
	public CategoryItem() { }
	
	public CategoryItem(Category category, Item item) {
		this.category = category;
		this.item = item;
	}
	
	public long getId() { return id; }

	public Item getItem() { return item; }
	public void setItem(Item item) { this.item = item; }
	
	public Category getCategory() { return category; }
	public void setCategory(Category category) {
		Optional.ofNullable(this.category)
			.ifPresent(c -> c.getCategoryItem().remove(this));

		this.category = category;

		if(!category.getCategoryItem().contains(this)) {
			category.getCategoryItem().add(this);
		}
	}	
}
