package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.AddReviewDTO;
import BakeryProject.demo.models.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews();
    void removeReviewById(Long id);
    AddReviewDTO findReviewById(Long id);

    void updateReview(AddReviewDTO addReviewDTO);
    void approveReview(Long id);
}
