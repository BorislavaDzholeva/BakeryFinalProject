package BakeryProject.demo.models.entity;

import jakarta.persistence.*;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String message;
    @Column(nullable = false)
    private boolean isApproved = false;
    @ManyToOne
    private User creator;
    @Column(name = "review_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime reviewDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reviews_products",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> productsReviews;

    public Review() {
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

    public User getCreator() {
        return creator;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Set<Product> getProductsReviews() {
        return productsReviews;
    }

    public void setProductsReviews(Set<Product> productsReviews) {
        this.productsReviews = productsReviews;
    }
}
