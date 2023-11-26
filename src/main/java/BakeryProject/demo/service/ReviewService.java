package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.CreateReviewDTO;
import BakeryProject.demo.models.view.ReviewView;

import java.util.List;

public interface ReviewService {
    List<ReviewView> getAllReviews();
    List<ReviewView> getApprovedReviews();
    void removeReviewById(Long id);
    void approveReview(Long id);

    void createReview(CreateReviewDTO createReviewDTO, String name);

}
