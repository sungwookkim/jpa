import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.entity.item.Book;
import domain.entity.item.abs.Item;
import repository.ItemRepository;
import service.ItemService;
import web.db.DBConfig;
import web.db.JpaConfig;
import web.db.vendor.h2.H2Config;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
classes = {
	H2Config.class
	, DBConfig.class
	, JpaConfig.class
	, AppConfig.class
})
@Transactional
public class ItemServiceTest {

	@Autowired ItemService itemService;
	@Autowired ItemRepository itemRepository;
	
	@Test
	public void save() {
		Item book = new Book("jpa", 100, 9000, "sinnake", "isbn");
		
		Long saveId = itemService.saveItem(book);

		Item getItem = itemService.findItem(saveId).get();

		assertEquals(book.getId(), getItem.getId());
	}
	
	@Test
	public void 상품수정() {
		Item book = new Book("jpa", 100, 9000, "sinnake", "isbn");
		
		Long saveId = itemService.saveItem(book);
		
		book.setPrice(10_000);
		book.setStockQuantity(10);

		saveId = itemService.saveItem(book);
		
		Item getItem = itemService.findItem(saveId).get();
				
		assertEquals("금액이 9,000원에서 10,000원으로 변경되어야 한다.", 10_000, getItem.getPrice());
		assertEquals("수량이 100개에서 10개로 변경되어야 한다.", 10, getItem.getStockQuantity());
	}
}
