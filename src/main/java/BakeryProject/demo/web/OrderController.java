package BakeryProject.demo.web;

import BakeryProject.demo.models.DTO.CreateOrderDTO;
import BakeryProject.demo.models.entity.CartItem;
import BakeryProject.demo.models.entity.UserEntity;
import BakeryProject.demo.models.view.CartItemView;
import BakeryProject.demo.service.CartService;
import BakeryProject.demo.service.OrderService;
import BakeryProject.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/orders")
public class OrderController {
    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(CartService cartService, OrderService orderService, UserService userService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createOrder(Principal principal, Model model) {
        List<CartItemView> cartItems = cartService.getCartItems(principal.getName());
        BigDecimal totalPrice = cartService.getTotalPrice(principal.getName());
        model.addAttribute("cartProducts", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "create-order";
    }

    @ModelAttribute
    public CreateOrderDTO createOrderDTO() {
        return new CreateOrderDTO();
    }

    @PostMapping("/create")
    public String createOrderConfirm(Principal principal, @Valid CreateOrderDTO createOrderDTO, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("createOrderDTO", createOrderDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createOrderDTO", bindingResult);

            return "redirect:/orders/create-order";
        }
        orderService.createOrder(createOrderDTO, principal.getName());

        return "redirect:/";
    }

    @GetMapping("/buy/{id}")
    public String buyProduct(@PathVariable Long id, Principal principal) {
        String currentUser = principal.getName();
        cartService.buyProductById(id, currentUser);
        return "redirect:/";
    }
}
