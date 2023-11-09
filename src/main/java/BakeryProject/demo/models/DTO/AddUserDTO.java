package BakeryProject.demo.models.DTO;

import BakeryProject.demo.models.entity.Card;
import BakeryProject.demo.models.entity.Order;
import BakeryProject.demo.models.entity.Review;
import BakeryProject.demo.models.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class AddUserDTO {
    private Long id;
    @Column(nullable = false)
    @Size(min = 3, max = 20, message = "First name length must be between 3 and 20 characters!")
    private String firstName;
    @Column(nullable = false)
    @Size(min = 3, max = 20, message = "Last name length must be between 3 and 20 characters!")
    private String lastName;
    @Column(nullable = false)
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
    private String username;
    @Column(nullable = false)
    @Size(min = 3, message = "Password length must be at least 3 characters!")
    private String password;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid.")
    private String email;
    @Column(nullable = false)
    @Size(min = 3, message = "City length must be at least 3 characters!")
    private String city;
    @Column(nullable = false)
    @Size(min = 3, message = "Address length must be at least 3 characters!")
    private String address;
    @Column(nullable = false)
    @Size(min = 9, max = 10, message = "Phone number length must be 10 numbers")
    private String phoneNumber;
    @NotNull(message = "You must select a role!")
    private RoleEnum role;

    public AddUserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
