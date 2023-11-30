package BakeryProject.demo.web;

import BakeryProject.demo.models.DTO.CreateReviewDTO;
import BakeryProject.demo.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/create/")
    public String createReview() {
        return "create-review";
    }
    @ModelAttribute
    public CreateReviewDTO createReviewDTO() {
        return new CreateReviewDTO();
    }

    @PostMapping("/create/")
    public String createReviewConfirm(Principal principal, @Valid CreateReviewDTO createReviewDTO, BindingResult
            bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("createReviewDTO", createReviewDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createReviewDTO", bindingResult);

            return "redirect:/reviews/create/";
        }
        reviewService.createReview(createReviewDTO, principal.getName());
        return "redirect:/";
    }
}
