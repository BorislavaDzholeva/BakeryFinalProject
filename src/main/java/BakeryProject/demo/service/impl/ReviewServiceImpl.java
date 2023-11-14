package BakeryProject.demo.service.impl;

import BakeryProject.demo.models.entity.Review;
import BakeryProject.demo.repository.ReviewRepository;
import BakeryProject.demo.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;


    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;

    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();

    }

    @Override
    public void removeReviewById(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public void approveReview(Long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review != null) {
            review.setApproved(true);
            reviewRepository.save(review);
        }

    }
}
