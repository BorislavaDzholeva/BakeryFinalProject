package BakeryProject.demo.web;

import BakeryProject.demo.models.entity.Review;
import BakeryProject.demo.models.view.ReviewView;
import BakeryProject.demo.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/review")
public class AdminReviewController {
    private final ReviewService reviewService;

    public AdminReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/")
    public String all(Model model) {
        List<ReviewView> allReviews = reviewService.getAllReviews();
        model.addAttribute("allReviews", allReviews);
        return "admin/review";
    }
    @GetMapping("/approve/{id}")
    public String approveReview(@PathVariable Long id) {
        reviewService.approveReview(id);
        return "redirect:/admin/review/";
    }
    @GetMapping("/removeReview/{id}")
    public String removeReview(@PathVariable Long id) {
        reviewService.removeReviewById(id);
        return "redirect:/admin/review/";
    }

}
