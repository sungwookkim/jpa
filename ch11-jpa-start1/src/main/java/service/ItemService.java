package service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.entity.item.abs.Item;
import repository.ItemRepository;

@Service
@Transactional
public class ItemService {

	@Autowired ItemRepository itemRepository;
	
	public Long saveItem(Item item) {
		itemRepository.save(item);
		
		return item.getId();
	}
	
	public List<Item> findItems() {
		return itemRepository.findAll();
	}
	
	public Item findItem(Long id) {
		return itemRepository.findOne(id);
	}
}
