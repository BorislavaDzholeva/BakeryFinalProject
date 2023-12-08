package BakeryProject.demo.web;

import BakeryProject.demo.service.CartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Map;

@RestController
public class CartRestController {
    private final CartService cartService;

    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/incrementItem/{id}")
    public Map<String, Map<String, String>> incrementItem(@PathVariable Long id, Principal principal) {
        int quantity = cartService.incrementItem(principal.getName(), id);
        BigDecimal totalPrice = cartService.getTotalPrice(principal.getName());
        // {"data":{"quantity":"2","total_price":"2.00"}}
        return Map.of("data", Map.of("quantity", String.valueOf(quantity),"total_price", String.valueOf(totalPrice)));
    }

    @GetMapping("/decrementItem/{id}")
    public Map<String, Map<String, String>> decrementItem(@PathVariable Long id, Principal principal) {
        int quantity = cartService.decrementItem(principal.getName(), id);
        BigDecimal totalPrice = cartService.getTotalPrice(principal.getName());
        return Map.of("data", Map.of("quantity", String.valueOf(quantity),"total_price", String.valueOf(totalPrice)));

    }
}
