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
    private final JavaMailSender mailSender;

    public HomeController(CategoryService categoryService, UserService userService, ReviewService reviewService, JavaMailSender mailSender) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.reviewService = reviewService;
        this.mailSender = mailSender;
    }

    @ModelAttribute
    public void allCategories(Model model, Principal principal) {
//        List<CategoryView> allCategories = categoryService.getAllCategories();
        List<ReviewView> allReviews = reviewService.getApprovedReviews();
//        if (principal != null) {
//            Cart cart = userService.findUserByUsername(principal.getName()).getCart();
//            model.addAttribute("userCart", cart);
//        }


//        model.addAttribute("allCategories", allCategories);
        model.addAttribute("allReviews", allReviews);
    }

    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/testMail")
    public String testMail(Model model) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("borislavadjoleva@abv.bg");
        message.setSubject("Bakery test mail");
        message.setText("This is a test mail from Bakery");
        mailSender.send(message);

        return "home";
    }

    @GetMapping("about")
    public String about(Model model) {
        return "about";
    }

    @GetMapping("contacts")
    public String contacts(Model model) {
        return "contacts";
    }

    @GetMapping("products")
    public String products(Model model) {
        return "products";
    }


}
