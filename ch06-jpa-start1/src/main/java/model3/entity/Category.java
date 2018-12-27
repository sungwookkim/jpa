package model3.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name = "CH06_MODEL3_CATEGORY")
@SequenceGenerator(
	name = "CH06_MODEL3_CATEGORY_SEQUENCE"
	, sequenceName = "CH06_MODEL3_CATEGORY_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "CH06_MODEL3_CATEGORY_SEQ")
	@Column(name = "CATEGORY_ID")
	private long id;
	private String name;

	@OneToMany(mappedBy = "category")
	private List<CategoryItem> categoryItems = new ArrayList<>();
	
	public Category() { }
	
	public Category(String name) {
		this.name = name;
	}
	
	public long getId() { return id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public List<CategoryItem> getCategoryItem() { return this.categoryItems; }
	public void addCategoryItem(CategoryItem categoryItem) {
		if(!this.categoryItems.contains(categoryItem)) {
			this.categoryItems.add(categoryItem);
		}

		if(categoryItem.getCategory() != this) {
			categoryItem.setCategory(this);
		}
	}
}
