package domain.entity.item.abs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import domain.entity.CategoryItem;

@Entity(name = "CH11_ITEM")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@SequenceGenerator(
	name = "CH11_ITEM_SEQUENCE"
	, sequenceName = "CH11_ITEM_SEQ"
	, initialValue = 1
	, allocationSize = 1)
public abstract class Item {

	@Id
	@Column(name = "ITEM_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH11_ITEM_SEQ")
	private Long id;
	
	@Column(name = "DTYPE", nullable = false, updatable = false, insertable = false)
	private String dType;
	
	private String name;
	
	private int price;
	
	private int stockQuantity;
	
	@OneToMany(mappedBy = "item", cascade=CascadeType.ALL)
	private List<CategoryItem> categoryItems = new ArrayList<>();
	
	public Item() {}
	public Item(String name, int price, int stockQuantity) {
		this.name = name;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}
	
	public Long getId() { return id; }
	
	public String getdType() { return dType; }
	public void setdType(String dType) { this.dType = dType; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }
	
	public int getStockQuantity() { return stockQuantity; }
	public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
	
	public List<CategoryItem> getCategoryItems() { return categoryItems; }
	public void addCategoryItems(CategoryItem categoryItem) {
		if(!this.categoryItems.contains(categoryItem)) {
			this.categoryItems.add(categoryItem);
		}
		
		if(categoryItem.getItem() != this) {
			categoryItem.setItem(this);
		}
	}
	
	public void addStock(int quantity) {
		this.stockQuantity += quantity;
	}
	
	public void removeStock(int quantity) {
		this.stockQuantity = Optional.ofNullable(this.stockQuantity - quantity)
			.filter(r -> r > 0)
			.map(r -> r)
			.orElseThrow(() -> {
				throw new IllegalStateException("수량이 부족합니다.");
			});
	}
}
