package BakeryProject.demo.models.view;

import BakeryProject.demo.models.entity.OrderItem;
import BakeryProject.demo.models.entity.Product;
import BakeryProject.demo.models.entity.UserEntity;
import java.math.BigDecimal;
import java.util.List;

public class OrderDetailsView {
    private Long id;
    private Product product;
    private Integer quantity;
    private BigDecimal price;
    private UserEntity user;
    public OrderDetailsView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

}
