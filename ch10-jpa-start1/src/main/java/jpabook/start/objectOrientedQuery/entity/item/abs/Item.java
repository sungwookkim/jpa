package jpabook.start.objectOrientedQuery.entity.item.abs;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH10_ITEM")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@SequenceGenerator(
	name = "CH10_ITEM_SEQUENCE"
	, sequenceName = "CH10_ITEM_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public abstract class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH10_ITEM_SEQ")
	@Column(name = "ITEM_ID")
	private long id;
	
	private String name;
	private int price;
	
	public long getId() { return id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }
	
}
