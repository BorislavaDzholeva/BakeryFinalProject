package BakeryProject.demo.web;

import BakeryProject.demo.models.entity.Product;
import BakeryProject.demo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/allProducts")
public class AdminAddProductController {
    private final ProductService productService;

    public AdminAddProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/")
    public String allProducts(Model model) {
        List<Product> allProducts = productService.getAll();
        model.addAttribute("allProducts", allProducts);
        return "/admin/all_products";
    }
}
