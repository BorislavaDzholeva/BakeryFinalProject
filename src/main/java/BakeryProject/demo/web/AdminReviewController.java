package BakeryProject.demo.web;

import BakeryProject.demo.models.DTO.AdminReviewDTO;
import BakeryProject.demo.models.entity.Review;
import BakeryProject.demo.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        List<Review> allReviews = reviewService.getAllReviews();
        model.addAttribute("allReviews", allReviews);
        return "/admin/review";
    }
    @GetMapping("/approve/{id}")
    public String approveReview(@PathVariable Long id) {
        reviewService.approveReview(id);
        return "redirect:/admin/review/";
    }
//    @PostMapping("/approve/")
//    public String approveReviewConfirm(@Valid AdminReviewDTO addReviewDTO, BindingResult bindingResult,
//                                    RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute
//                    ("addReviewDTO", addReviewDTO);
//            redirectAttributes.addFlashAttribute
//                    ("org.springframework.validation.BindingResult.addReviewDTO", bindingResult);
//            return "redirect:/admin/review/approve/" + addReviewDTO.getId();
//        }
//        reviewService.approveReview(addReviewDTO.getId());
//        return "redirect:/admin/review/";
//    }
    @GetMapping("/removeReview/{id}")
    public String removeCategory(@PathVariable Long id) {
        reviewService.removeReviewById(id);
        return "redirect:/admin/review/";
    }

}
