package BakeryProject.demo.service.impl;

import BakeryProject.demo.models.DTO.AddCategoryDTO;
import BakeryProject.demo.models.DTO.AddReviewDTO;
import BakeryProject.demo.models.entity.Category;
import BakeryProject.demo.models.entity.Review;
import BakeryProject.demo.repository.ReviewRepository;
import BakeryProject.demo.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
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
    public AddReviewDTO findReviewById(Long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        return modelMapper.map(review, AddReviewDTO.class);
    }

    @Override
    public void updateReview(AddReviewDTO addReviewDTO) {
        Review review = reviewRepository.findById(addReviewDTO.getId()).orElse(null);
        if(review != null){
            modelMapper.map(review, AddReviewDTO.class);
            reviewRepository.save(review);
        }
    }

    @Override
    public void approveReview(Long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if(review != null){
            review.setApproved(true);
            reviewRepository.save(review);
        }

    }
}
