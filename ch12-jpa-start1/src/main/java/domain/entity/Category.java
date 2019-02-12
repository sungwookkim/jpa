package domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH12_CATEGORY")
@SequenceGenerator(
	name = "CH12_CATEGORY_SEQUENCE"
	, sequenceName = "CH12_CATEGORY_SEQ"
	, initialValue = 1
	, allocationSize = 1)
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH12_CATEGORY_SEQ")
	@Column(name = "CATEGORY_ID")
	private long id;
	
	private String name;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private List<CategoryItem> categoryItems = new ArrayList<>();
	
	public Category() { }
	
	public Category(String name) {
		this.name = name;
	}

	public long getId() { return id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public List<CategoryItem> getCategoryItems() { return categoryItems; }
	public void addCategoryItems(CategoryItem categoryItem) {
		if(!this.categoryItems.contains(categoryItem)) {
			this.categoryItems.add(categoryItem);
		}
		
		if(categoryItem.getCategory() != this) {
			categoryItem.setCategory(this);
		}
	}
}
