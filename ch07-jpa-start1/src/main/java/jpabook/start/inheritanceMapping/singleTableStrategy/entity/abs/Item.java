package jpabook.start.inheritanceMapping.singleTableStrategy.entity.abs;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

@Entity(name = "CH07_SINGLETABLE_STRATEGY_ITEM")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
/*
 * 부모 클래스에 구분 컬럼을 지정한다. 이 컬럼으로 저장된 자식 테이블을 구분할 수 있다.
 * 기본값이 DTYPE이므로 @DiscriminatorColumn으로 줄여 사용해도 된다. 
 */
@DiscriminatorColumn(name = "DTYPE")
@SequenceGenerator(
	name = "CH07_SINGLETABLE_ITEM_SEQUENCE"
	, sequenceName = "CH07_SINGLETABLE_ITEM_SEQ"
	, initialValue = 1
	, allocationSize = 1
)
public abstract class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CH07_SINGLETABLE_ITEM_SEQ")
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
