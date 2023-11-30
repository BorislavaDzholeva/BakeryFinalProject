package BakeryProject.demo.models.DTO;
import BakeryProject.demo.models.entity.UserEntity;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class CreateReviewDTO {
    @Size(min = 3, message = "Message must be at least 3 characters long.")
    private String message;
    private UserEntity creator;
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
