package BakeryProject.demo.web;

import BakeryProject.demo.models.DTO.AddProductDTO;
import BakeryProject.demo.models.entity.Category;
import BakeryProject.demo.models.entity.Product;
import BakeryProject.demo.service.CategoryService;
import BakeryProject.demo.service.ProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images/";

    public AdminProductController(ProductService productService, ModelMapper modelMapper, CategoryService categoryService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }


    @GetMapping("/")
    public String allProducts(Model model) {
        List<Product> allProducts = productService.getAll();
        model.addAttribute("allProducts", allProducts);
        return "/admin/products";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        List<Category> allCategories = categoryService.getAllCategories();
        model.addAttribute("allCategories", allCategories);
        return "/admin/add_product";
    }

    @ModelAttribute
    public AddProductDTO addProductDTO() {
        return new AddProductDTO();
    }

    @PostMapping("/add")
    public String addProductConfirm(@RequestParam("image") MultipartFile file, @Valid AddProductDTO addProductDTO, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addProductDTO", addProductDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addProductDTO", bindingResult);

            return "redirect:/admin/products/add";
        }

        StringBuilder fileName = new StringBuilder();
        Path fileNameAndPath = Path.of(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileName.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        Product product = modelMapper.map(addProductDTO, Product.class);
        product.setProductImage("/images/" + file.getOriginalFilename());
        productService.addProduct(product);
        return "redirect:/admin/products/";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        AddProductDTO productData = productService.findProductById(id);
        List<Category> allCategories = categoryService.getAllCategories();
        model.addAttribute("allCategories", allCategories);
        model.addAttribute("productData", productData);
        return "admin/edit_product";
    }

    @PostMapping("/edit/")
    public String editProductConfirm(@Valid AddProductDTO addProductDTO, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute
                    ("addProductDTO", addProductDTO);
            redirectAttributes.addFlashAttribute
                    ("org.springframework.validation.BindingResult.addProductDTO", bindingResult);
            return "redirect:/admin/products/edit/" + addProductDTO.getId();
        }
        productService.updateProduct(addProductDTO);
        return "redirect:/admin/products/";
    }

    @GetMapping("/removeProduct/{id}")
    public String removeProduct(@PathVariable Long id) {
        productService.removeProductById(id);
        return "redirect:/admin/products/";
    }
}
