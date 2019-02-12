package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.entity.item.abs.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
