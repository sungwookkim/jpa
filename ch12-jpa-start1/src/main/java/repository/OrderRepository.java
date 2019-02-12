package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import domain.entity.Order;
import repository.queryDSL.inter.CustomOrderRepository;

public interface OrderRepository extends JpaRepository<Order, Long>
	, CustomOrderRepository
	, JpaSpecificationExecutor<Order>{

}
