package BakeryProject.demo.web;

import BakeryProject.demo.exception.BlockedIPException;
import BakeryProject.demo.exception.ObjectNotFoundException;
import BakeryProject.demo.models.entity.Cart;
import BakeryProject.demo.models.view.CategoryView;
import BakeryProject.demo.service.CategoryService;
import BakeryProject.demo.service.UserService;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@ControllerAdvice
@Controller
public class ErrController implements ErrorController {
    private final CategoryService categoryService;
    private final UserService userService;

    @ModelAttribute
    public void commonProps(Model model, Principal principal) {
        List<CategoryView> allCategories = categoryService.getAllCategories();
        model.addAttribute("allCategories", allCategories);
        if (principal != null) {
            Cart cart = userService.findUserByUsername(principal.getName()).getCart();
            model.addAttribute("userCart", cart);
        }
    }

    public ErrController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }
    @ExceptionHandler(Exception.class)
    @RequestMapping("/error")
    public String handleServerError(Exception exception, Model model) {
        List<CategoryView> allCategories = categoryService.getAllCategories();
        model.addAttribute("allCategories", allCategories);
        model.addAttribute("message", exception.getMessage());
        return "/500";
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public String handleProductNotFound(ObjectNotFoundException exception, Model model) {
        List<CategoryView> allCategories = categoryService.getAllCategories();
        model.addAttribute("allCategories", allCategories);
        model.addAttribute("message", exception.getMessage());
        return "/404";
    }

    @ExceptionHandler(BlockedIPException.class)
    public String handleProductNotFound(BlockedIPException exception, Model model) {
        return "/403";
    }
}
