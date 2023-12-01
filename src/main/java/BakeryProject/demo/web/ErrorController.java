package BakeryProject.demo.web;

import BakeryProject.demo.exception.ProductNotFoundException;
import BakeryProject.demo.models.view.CategoryView;
import BakeryProject.demo.service.CategoryService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
@ControllerAdvice
public class ErrorController {
    private final CategoryService categoryService;

    public ErrorController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFound(ProductNotFoundException exception, Model model) {
        List<CategoryView> allCategories = categoryService.getAllCategories();
        model.addAttribute("allCategories", allCategories);
        model.addAttribute("message", exception.getMessage());
        return "/404";
    }

}
