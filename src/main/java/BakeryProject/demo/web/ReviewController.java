package BakeryProject.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @GetMapping("/create")
    public String createReview() {
        return "create-review";
    }
}
