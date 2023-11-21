package BakeryProject.demo.web;

import BakeryProject.demo.models.entity.Category;
import BakeryProject.demo.models.view.CategoryView;
import BakeryProject.demo.service.CategoryService;
import BakeryProject.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@ControllerAdvice
@RequestMapping("/")
public class HomeController {
    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }
    @ModelAttribute
    public void allCategories(Model model) {
        List<CategoryView> allCategories = categoryService.getAllCategories();
        model.addAttribute("allCategories", allCategories);
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
