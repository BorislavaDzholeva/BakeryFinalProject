package BakeryProject.demo.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIT {


    @Autowired
    MockMvc mockMvc;


    @Test
    void testCreateOrderGet() throws Exception {
        this.mockMvc.perform(get("/orders/create/").with(user("ivan80"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("create-order"));
    }

    @Test
    void testCreateOrderConfirm() throws Exception {
        this.mockMvc.perform(post("/orders/create/").with(user("ivan80"))
                        .param("address", "Sofia")
                        .param("phone", "0888888888")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/orders/order-confirmation/"));

    }

    @Test
    void testCreateOrderConfirmWithInvalidData() throws Exception {
        this.mockMvc.perform(post("/orders/create/").with(user("ivan80"))
                        .param("address", "")
                        .param("phone", "0888888888")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/orders/create/"));

    }

    @Test
    void testOrderConfirmation() throws Exception {
        this.mockMvc.perform(get("/orders/order-confirmation/").with(user("ivan80"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("order-confirmation"));
    }


}
