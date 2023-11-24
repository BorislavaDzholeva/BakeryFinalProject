package BakeryProject.demo.models.DTO;
import BakeryProject.demo.models.entity.UserEntity;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class CreateReviewDTO {
    private String message;
    private UserEntity creator;
    @PastOrPresent
    private LocalDateTime reviewDate = LocalDateTime.now();

    public CreateReviewDTO() {
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
