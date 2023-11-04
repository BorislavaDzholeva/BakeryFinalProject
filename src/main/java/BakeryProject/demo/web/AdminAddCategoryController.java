package BakeryProject.demo.web;

import BakeryProject.demo.models.DTO.AddCategoryDTO;
import BakeryProject.demo.models.entity.Category;
import BakeryProject.demo.models.entity.User;
import BakeryProject.demo.service.CategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/category")
public class AdminAddCategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public AdminAddCategoryController(CategoryService categoryService, ModelMapper modelMapper) {
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
    public AddCategoryDTO addCategoryDTO() {
        return new AddCategoryDTO();
    }

    @PostMapping("/add")
    public String addCategoryConfirm(@Valid AddCategoryDTO addCategoryDTO, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addCategoryDTO", addCategoryDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addCategoryDTO", bindingResult);

            return "redirect:/add";
        }
        categoryService.addCategory(modelMapper.map(addCategoryDTO, Category.class));
        return "redirect:/admin/category/";
    }
}
