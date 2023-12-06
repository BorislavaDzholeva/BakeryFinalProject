package BakeryProject.demo.web;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLoginGet() throws Exception {
        this.mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }


    @Test
    void testRegisterGet() throws Exception {
        this.mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    @Transactional
    void testRegisterConfirm() throws Exception {
        this.mockMvc.perform(post("/users/register")
                        .param("firstName", "Borislava")
                        .param("lastName", "Ivanova")
                        .param("username", "borislava4")
                        .param("password", "123456")
                        .param("confirmPassword", "123456")
                        .param("email", "borislava5@abv.bg")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/login"));

    }

    @Test
    void testRegisterConfirmWithInvalidData() throws Exception {
        this.mockMvc.perform(post("/users/register")
                        .param("firstName", "Borislava")
                        .param("lastName", "Ivanova")
                        .param("username", "borislava")
                        .param("password", "123456")
                        .param("confirmPassword", "1234567")
                        .param("email", "borislava@abv.bg")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/register"));

    }

    @Test
    void testProfile() throws Exception {
        this.mockMvc.perform(get("/users/profile/").with(user("admin"))
                        .param("username", "admin")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"));
    }


    @Test
    void testOrderDetails() throws Exception {
        this.mockMvc.perform(get("/users/orderDetails/1").with(user("admin"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("order-details"));
    }

}
