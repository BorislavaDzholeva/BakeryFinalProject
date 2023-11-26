package BakeryProject.demo.models.view;

import BakeryProject.demo.models.entity.UserEntity;

import java.time.LocalDateTime;

public class ReviewView {

    private Long id;
    private String message;
    private UserEntity creator;
    private LocalDateTime reviewDate = LocalDateTime.now();
    private boolean isApproved;


    public ReviewView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }


}
