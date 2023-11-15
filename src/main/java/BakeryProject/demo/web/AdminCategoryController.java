package BakeryProject.demo.web;

import BakeryProject.demo.models.DTO.AdminAddCategoryDTO;
import BakeryProject.demo.models.entity.Category;
import BakeryProject.demo.service.CategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        List<Category> allCategories = categoryService.getAllCategories();
        model.addAttribute("allCategories", allCategories);
        return "/admin/category";
    }

    @GetMapping("/add")
    public String categoryAdd(Model model) {
        return "/admin/add_category";
    }

    @ModelAttribute
    public AdminAddCategoryDTO adminAddCategoryDTO() {
        return new AdminAddCategoryDTO();
    }

    @PostMapping("/add")
    public String addCategoryConfirm(@Valid AdminAddCategoryDTO adminAddCategoryDTO, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("adminAddCategoryDTO", adminAddCategoryDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.adminAddCategoryDTO", bindingResult);

            return "redirect:/admin/category/add";
        }
        categoryService.addCategory(modelMapper.map(adminAddCategoryDTO, Category.class));
        return "redirect:/admin/category/";
    }
    @GetMapping("/edit/{id}")
    public String categoryEdit(@PathVariable Long id, Model model) {
        AdminAddCategoryDTO categoryData = categoryService.findCategoryById(id);
        model.addAttribute("categoryData", categoryData);
        return "admin/edit_category";
    }
    @PostMapping("/edit/")
    public String userEditConfirm(@Valid AdminAddCategoryDTO adminAddCategoryDTO, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute
                    ("adminAddCategoryDTO", adminAddCategoryDTO);
            redirectAttributes.addFlashAttribute
                    ("org.springframework.validation.BindingResult.adminAddCategoryDTO", bindingResult);
            return "redirect:/admin/category/edit/" + adminAddCategoryDTO.getId();
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
