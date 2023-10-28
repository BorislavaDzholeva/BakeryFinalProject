package BakeryProject.demo.web;

import BakeryProject.demo.models.entity.Product;
import BakeryProject.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String all(Model model) {
        List<Product> all = productService.getAll();
        model.addAttribute("products", all);
        return "products";
    }
}
