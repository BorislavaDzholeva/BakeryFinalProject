package BakeryProject.demo.web;

import BakeryProject.demo.models.view.CartItemView;
import BakeryProject.demo.service.CartService;
import BakeryProject.demo.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    protected Optional<String> getPreviousPageByRequest(HttpServletRequest request)
    {
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
    }
    @GetMapping("/details")
    public String cartDetails(Principal principal, Model model) {
        List<CartItemView> cartItems = cartService.getCartItems(principal.getName());
        BigDecimal totalPrice = cartService.getTotalPrice(principal.getName());
        model.addAttribute("cartProducts", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "cart-details";
    }
    @GetMapping("/buy/{id}")
    public String buyProduct(@PathVariable Long id, Principal principal, HttpServletRequest request) {
        String currentUser = principal.getName();
        cartService.buyProductById(id, currentUser);
        return getPreviousPageByRequest(request).orElse("redirect:/");
    }

    @GetMapping("/removeItem/{id}")
    public String removeItem(@PathVariable Long id, Principal principal) {
        cartService.removeProductFromCart(principal.getName(), id);
        return "redirect:/cart/details";
    }
    @GetMapping("/incrementItem/{id}")
    public String incrementItem(@PathVariable Long id, Principal principal) {
        cartService.incrementItem(principal.getName(), id);
        return "redirect:/cart/details";
    }
    @GetMapping("/decrementItem/{id}")
    public String decrementItem(@PathVariable Long id, Principal principal) {
        cartService.decrementItem(principal.getName(), id);
        return "redirect:/cart/details";
    }
}
