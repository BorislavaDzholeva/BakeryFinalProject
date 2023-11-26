package BakeryProject.demo.service.impl;

import BakeryProject.demo.models.DTO.CreateReviewDTO;
import BakeryProject.demo.models.entity.Review;
import BakeryProject.demo.models.entity.UserEntity;
import BakeryProject.demo.models.view.ReviewView;
import BakeryProject.demo.repository.ReviewRepository;
import BakeryProject.demo.repository.UserRepository;
import BakeryProject.demo.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReviewView> getAllReviews() {
        return reviewRepository.
                findAll().stream().map(review -> modelMapper.map(review, ReviewView.class)).toList();
    }

    @Override
    public List<ReviewView> getApprovedReviews() {
        return reviewRepository.
                findAllByIsApprovedTrue().stream().map(review -> modelMapper.map(review, ReviewView.class)).toList();
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

    @Override
    public void createReview(CreateReviewDTO createReviewDTO, String name) {
        Review review = modelMapper.map(createReviewDTO, Review.class);
        UserEntity user = userRepository.findByUsername(name).orElse(null);
        review.setCreator(user);
        review.setReviewDate(LocalDateTime.now());
        reviewRepository.save(review);
    }
}
