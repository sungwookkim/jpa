package service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.entity.item.abs.Item;
import repository.ItemRepository;

@Service
@Transactional
public class ItemService {

	final private ItemRepository itemRepository;
	
	@Autowired
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	public Long saveItem(Item item) {
		itemRepository.save(item);
		
		return item.getId();
	}
	
	public List<Item> findItems() {
		return itemRepository.findAll();
	}
	
	public Optional<Item> findItem(Long id) {
		return itemRepository.findById(id);
	}
}
