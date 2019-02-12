package repository.queryDSL.inter;

import java.util.List;

import domain.OrderSearch;
import domain.entity.Order;

public interface CustomOrderRepository {
	
	public List<Order> search(OrderSearch orderSearch);
}
