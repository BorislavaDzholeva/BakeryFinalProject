package BakeryProject.demo.service;

import BakeryProject.demo.models.view.CartItemView;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

        void buyProductById(Long productId, String currentUser);

        List<CartItemView> getCartItems(String currentUser);
        BigDecimal getTotalPrice(String currentUser);
}
