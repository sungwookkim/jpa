import static org.junit.Assert.assertEquals;

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
//@Transactional
public class ItemServiceTest {

	@Autowired ItemService itemService;
	@Autowired ItemRepository itemRepository;
	
	@Test
	public void save() {
		Item book = new Book("jpa", 100, 9000, "sinnake", "isbn");
		
		Long saveId = itemService.saveItem(book);

		assertEquals(book.getId(), itemService.findItem(saveId).getId());
	}
}
