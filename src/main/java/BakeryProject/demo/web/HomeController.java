package BakeryProject.demo.web;

import BakeryProject.demo.models.entity.Cart;
import BakeryProject.demo.models.view.CategoryView;
import BakeryProject.demo.service.CategoryService;
import BakeryProject.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@ControllerAdvice
@RequestMapping("/")
public class HomeController {
    private final CategoryService categoryService;
    private final UserService userService;
    public HomeController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @ModelAttribute
    public void allCategories(Model model, Principal principal) {
        List<CategoryView> allCategories = categoryService.getAllCategories();
        if (principal != null) {
            Cart cart = userService.findUserByUsername(principal.getName()).getCart();
            model.addAttribute("userCart", cart);
        }
        model.addAttribute("allCategories", allCategories);
    }

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }


    @GetMapping("about")
    public String about(Model model) {
        return "about";
    }

    @GetMapping("contacts")
    public String contacts(Model model) {
        return "contacts";
    }

    @GetMapping("products")
    public String products(Model model) {
        return "products";
    }


}
