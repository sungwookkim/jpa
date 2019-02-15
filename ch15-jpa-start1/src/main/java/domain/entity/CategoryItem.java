package domain.entity;

import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import domain.entity.item.abs.Item;

@Entity(name = "CH15_CATEGORYITEM")
@SequenceGenerator(
	name = "CH15_CATEGORYITEM_SEQUENCE"
	, sequenceName = "CH15_CATEGORYITEM_SEQ"
	, initialValue = 1
	, allocationSize = 1)
public class CategoryItem {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH15_CATEGORYITEM_SEQ")
	@Column(name = "CATEGORY_ITEM_ID")
	private long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ITEM_ID")
	private Item item;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;
	
	public CategoryItem() {  }

	public long getId() { return id; }

	public Item getItem() { return item; }
	public void setItem(Item item) {
		Optional.ofNullable(this.item)
			.ifPresent(i -> item.getCategoryItems().remove(this));
		
		this.item = item;
		
		if(!item.getCategoryItems().contains(this)) {
			item.getCategoryItems().add(this);
		}
	}

	public Category getCategory() { return category; }
	public void setCategory(Category category) {
		Optional.ofNullable(this.category)
			.ifPresent(c -> category.getCategoryItems().remove(this));
		
		this.category = category;
		
		if(!category.getCategoryItems().contains(this)) {
			category.getCategoryItems().add(this);
		}
	}
}
