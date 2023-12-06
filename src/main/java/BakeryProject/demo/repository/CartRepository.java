package BakeryProject.demo.repository;

import BakeryProject.demo.models.entity.Cart;
import BakeryProject.demo.models.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
