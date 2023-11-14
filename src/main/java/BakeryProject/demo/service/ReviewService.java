package BakeryProject.demo.service;

import BakeryProject.demo.models.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews();
    void removeReviewById(Long id);
    void approveReview(Long id);
}
