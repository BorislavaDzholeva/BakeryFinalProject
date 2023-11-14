package BakeryProject.demo.models.DTO;

import BakeryProject.demo.models.entity.Category;
import BakeryProject.demo.models.enums.AvailabilityEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class AdminAddProductDTO {
    private Long id;
    @Column(nullable = false, unique = true)
    @Size(min = 3, message = "Product name must be at least 3 characters!")
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(min = 10, message = "Description of the product must be at least 10 characters!")
    private String description;
    @Column(name = "product_image")
    //@NotEmpty(message = "You should add product picture!")
    private String productImage;
    @Column(nullable = false)
    @Positive(message = "The weight must be positive number!")
    private int weight;
    @NotNull(message = "You should add product price!")
    @Positive(message = "The price must be positive number!")
    private BigDecimal price;
    @Column(columnDefinition = "TEXT")
    @NotEmpty(message = "You should add at least 1 allergen!")
    private String allergens;
    @Column(nullable = false)
    private AvailabilityEnum availability;
    @Column(columnDefinition = "TEXT")
    @NotEmpty(message = "You should add at least 1 ingredient!")
    private String ingredients;
    @ManyToOne
    @NotNull
    private Category category;

    public AdminAddProductDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public AvailabilityEnum getAvailability() {
        return availability;
    }

    public void setAvailability(AvailabilityEnum availability) {
        this.availability = availability;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
