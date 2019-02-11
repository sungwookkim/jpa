package repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import domain.entity.item.abs.Item;

@Repository
public class ItemRepository {

	@PersistenceContext
	EntityManager em;
	
	public void save(Item item) {
		Optional.ofNullable(item.getId())
			.map(i -> {
				em.merge(item);
				return i;
			})
			.orElseGet(() -> {
				em.persist(item);

				return item.getId();
			});
	}
	
	public Item findOne(Long id) {
		return em.find(Item.class, id);
	}
	
	public List<Item> findAll() {
		return em.createQuery("SELECT i FROM CH11_ITEM i", Item.class).getResultList();
	}
}
