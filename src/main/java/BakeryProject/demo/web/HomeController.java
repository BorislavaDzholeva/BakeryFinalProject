package BakeryProject.demo.web;

import BakeryProject.demo.models.entity.Category;
import BakeryProject.demo.service.CategoryService;
import BakeryProject.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public HomeController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Category> allCategories = categoryService.getAllCategories();
        model.addAttribute("allCategories", allCategories);
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
    @GetMapping("product")
    public String products(Model model) {
        return "products";
    }
}
