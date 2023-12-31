package BakeryProject.demo.web;

import BakeryProject.demo.models.view.ReviewView;
import BakeryProject.demo.service.CategoryService;
import BakeryProject.demo.service.ReviewService;
import BakeryProject.demo.service.UserService;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final CategoryService categoryService;
    private final UserService userService;
    private final ReviewService reviewService;

    public HomeController(CategoryService categoryService, UserService userService, ReviewService reviewService) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.reviewService = reviewService;

    }

    @ModelAttribute
    public void allCategories(Model model) {
        List<ReviewView> allReviews = reviewService.getApprovedReviews();
        model.addAttribute("allReviews", allReviews);
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("about")
    public String about() {
        return "about";
    }

    @GetMapping("contacts")
    public String contacts() {
        return "contacts";
    }

    @GetMapping("products")
    public String products() {
        return "products";
    }


}
