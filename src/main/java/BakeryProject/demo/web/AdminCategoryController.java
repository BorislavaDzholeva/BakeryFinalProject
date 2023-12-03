package BakeryProject.demo.web;

import BakeryProject.demo.models.DTO.AdminAddCategoryDTO;
import BakeryProject.demo.models.entity.Category;
import BakeryProject.demo.models.entity.Product;
import BakeryProject.demo.models.view.CategoryView;
import BakeryProject.demo.service.CategoryService;
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
@RequestMapping("/admin/category")
public class AdminCategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public AdminCategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/")
    public String all(Model model) {
        List<CategoryView> allCategories = categoryService.getAllCategories();
        model.addAttribute("allCategories", allCategories);
        return "/admin/category";
    }

    @GetMapping("/add/")
    public String categoryAdd() {
        return "/admin/add_category";
    }

    @ModelAttribute
    public AdminAddCategoryDTO adminAddCategoryDTO() {
        return new AdminAddCategoryDTO();
    }

    @PostMapping("/add/")
    public String addCategoryConfirm(@RequestParam("image") MultipartFile file, @Valid AdminAddCategoryDTO adminAddCategoryDTO, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("adminAddCategoryDTO", adminAddCategoryDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.adminAddCategoryDTO", bindingResult);

            return "redirect:/admin/category/add/";
        }
        String imageUrl = categoryService.uploadCategoryImage(file);
        Category category = modelMapper.map(adminAddCategoryDTO, Category.class);
        category.setImageUrl(imageUrl);
        categoryService.addCategory(category);
        return "redirect:/admin/category/";
    }
    @GetMapping("/edit/{id}")
    public String categoryEdit(@PathVariable Long id, Model model) {
        AdminAddCategoryDTO categoryData = categoryService.findCategoryById(id);
        model.addAttribute("categoryData", categoryData);
        return "/admin/edit_category";
    }
    @PostMapping("/edit/")
    public String categoryEditConfirm(@RequestParam("image") MultipartFile file,@Valid AdminAddCategoryDTO adminAddCategoryDTO, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute
                    ("adminAddCategoryDTO", adminAddCategoryDTO);
            redirectAttributes.addFlashAttribute
                    ("org.springframework.validation.BindingResult.adminAddCategoryDTO", bindingResult);
            return "redirect:/admin/category/edit/" + adminAddCategoryDTO.getId();
        }
        if (!file.isEmpty()) {
            String imageUri = categoryService.uploadCategoryImage(file);
            adminAddCategoryDTO.setImageUrl(imageUri);
        }
        categoryService.updateCategory(adminAddCategoryDTO);
        return "redirect:/admin/category/";
    }
    @GetMapping("/removeCategory/{id}")
    public String removeCategory(@PathVariable Long id) {
        categoryService.removeCategoryById(id);
        return "redirect:/admin/category/";
    }
}
