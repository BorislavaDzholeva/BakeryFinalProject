package BakeryProject.demo.repository;

import BakeryProject.demo.models.entity.Order;
import BakeryProject.demo.models.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
