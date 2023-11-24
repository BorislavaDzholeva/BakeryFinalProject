package BakeryProject.demo.models.DTO;

import BakeryProject.demo.models.entity.OrderItem;
import BakeryProject.demo.models.entity.Product;
import BakeryProject.demo.models.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CreateOrderDTO {
    @Column(nullable = false)
    @Size(min = 3, message = "City length must be at least 3 characters!")
    private String city;
    @Column(nullable = false)
    @Size(min = 5, message = "Address length must be at least 5 characters!")
    private String address;
    @Column(nullable = false)
    @Size(min = 9, max = 10, message = "Phone number length must be 10 numbers")
    private String phoneNumber;

    public CreateOrderDTO() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
