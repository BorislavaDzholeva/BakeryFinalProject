package BakeryProject.demo.models.DTO;

import BakeryProject.demo.validation.FieldMath;
import BakeryProject.demo.validation.UniqueEmail;
import BakeryProject.demo.validation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@FieldMath(
        first = "password",
        second = "confirmPassword",
        message = "Passwords do not match."
)

public class UserRegistrationDTO {
    @NotNull
    @Size(min =3, max = 20,message = "First name length must be between 3 and 20 characters!")
    private String firstName;
    @NotNull
    @Size(min =3, max = 20,message = "Last name length must be between 3 and 20 characters!")
    private String lastName;
    @NotNull
    @Size(min = 3, max = 20,message = "Username length must be between 3 and 20 characters!")
    @UniqueUsername(message = "Username should be unique and not taken.")
    private String username;
    @NotNull
    @Size(min =3, max = 20,message = "Password length must be between 3 and 20 characters!")
    private String password;
    @NotNull
    @Size(min =3, max = 20,message = "Passwords should match!")
    private String confirmPassword;
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid.")
    @UniqueEmail(message = "Email should be unique and not taken.")
    private String email;

    public UserRegistrationDTO() {
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
