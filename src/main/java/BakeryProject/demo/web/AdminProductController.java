package BakeryProject.demo.web;

import BakeryProject.demo.models.DTO.AdminAddProductDTO;
import BakeryProject.demo.models.entity.Category;
import BakeryProject.demo.models.entity.Product;
import BakeryProject.demo.models.view.CategoryView;
import BakeryProject.demo.service.CategoryService;
import BakeryProject.demo.service.ProductService;
import BakeryProject.demo.models.view.ProductsView;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    public AdminProductController(ProductService productService, ModelMapper modelMapper, CategoryService categoryService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }


    @GetMapping("/")
    public String allProducts(Model model) {
        List<ProductsView> allProducts = productService.getAll();
        model.addAttribute("allProducts", allProducts);
        return "/admin/products";
    }

    @GetMapping("/add/")
    public String addProduct(Model model) {
        List<CategoryView> allCategories = categoryService.getAllCategories();
        model.addAttribute("allCategories", allCategories);
        return "/admin/add_product";
    }

    @ModelAttribute
    public AdminAddProductDTO adminAddProductDTO() {
        return new AdminAddProductDTO();
    }

    @PostMapping("/add/")
    public String addProductConfirm(@RequestParam("image") MultipartFile file, @Valid AdminAddProductDTO adminAddProductDTO, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("adminAddProductDTO", adminAddProductDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.adminAddProductDTO", bindingResult);

            return "redirect:/admin/products/add/";
        }

        String imageUrl = productService.uploadProductImage(file);
        Product product = modelMapper.map(adminAddProductDTO, Product.class);
        product.setProductImage(imageUrl);
        productService.addProduct(product);
        return "redirect:/admin/products/";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        AdminAddProductDTO adminAddProductDTO = productService.findProductById(id);
        List<CategoryView> allCategories = categoryService.getAllCategories();
        model.addAttribute("allCategories", allCategories);
        model.addAttribute("adminAddProductDTO", adminAddProductDTO);
        return "admin/edit_product";
    }

    @PostMapping("/edit/")
    public String editProductConfirm(@RequestParam("image") MultipartFile file, @Valid AdminAddProductDTO adminAddProductDTO, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("adminAddProductDTO", adminAddProductDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.adminAddProductDTO", bindingResult);
            return "redirect:/admin/products/edit/" + adminAddProductDTO.getId();
        }
        if (!file.isEmpty()) {
            String imageUri = productService.uploadProductImage(file);
            adminAddProductDTO.setProductImage(imageUri);
        }
        productService.updateProduct(adminAddProductDTO);
        return "redirect:/admin/products/";
    }

    @GetMapping("/removeProduct/{id}")
    public String removeProduct(@PathVariable Long id) {
        productService.removeProductById(id);
        return "redirect:/admin/products/";
    }
}
