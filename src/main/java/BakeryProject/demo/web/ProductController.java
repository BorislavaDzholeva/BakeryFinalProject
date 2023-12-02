package BakeryProject.demo.web;

import BakeryProject.demo.models.entity.Product;
import BakeryProject.demo.models.view.CategoryView;
import BakeryProject.demo.service.CategoryService;
import BakeryProject.demo.service.ProductService;
import BakeryProject.demo.models.view.ProductsView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String all(Model model) {
        String pageName = "All Products";
        List<ProductsView> allProducts = productService.getAll();
        model.addAttribute("allProducts", allProducts);
        model.addAttribute("pageName", pageName);
        return "products";
    }

    @GetMapping("single-product/{id}")
    public String singleProductPage(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "single-product";
    }


    @GetMapping("productsByCategory/{id}")
    public String productsByCategory(@PathVariable("id") Long id, Model model) {
        List<Product> productsByCategory = productService.findAllProductsByCategoryId(id);
        model.addAttribute("allProducts", productsByCategory);
        model.addAttribute("pageName", productsByCategory.get(0).getCategory().getName());
        return "products";
    }

    @ModelAttribute
    public void allCategories(Model model) {
        List<CategoryView> allCategories = categoryService.getAllCategories();
        model.addAttribute("allCategories", allCategories);
    }
}
