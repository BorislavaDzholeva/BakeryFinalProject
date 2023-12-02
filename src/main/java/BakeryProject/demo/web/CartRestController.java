package BakeryProject.demo.web;

import BakeryProject.demo.repository.UserRepository;
import BakeryProject.demo.service.CartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class CartRestController {
    private final CartService cartService;

    public CartRestController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/incrementItem/{id}")
    public Map<String, Integer> incrementItem(@PathVariable Long id, Principal principal) {
        int quantity = cartService.incrementItem(principal.getName(), id);
        return Map.of("quantity", quantity);
    }

    @GetMapping("/decrementItem/{id}")
    public Map<String, Integer> decrementItem(@PathVariable Long id, Principal principal) {
        int quantity = cartService.decrementItem(principal.getName(), id);
        return Map.of("quantity", quantity);
    }
}
