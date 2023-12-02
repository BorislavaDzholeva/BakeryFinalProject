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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CartControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testCartDetails() throws Exception {
        mockMvc.perform(get("/cart/details").with(user("admin")))
                .andExpect(status().isOk())
                .andExpect(view().name("cart-details"));
    }

    @Test
    public void testBuyProduct() throws Exception {
        mockMvc.perform(get("/cart/buy/1").with(user("admin"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testRemoveItem() throws Exception {
        mockMvc.perform(get("/cart/removeItem/1").with(user("admin"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testIncrementItem() throws Exception {
        mockMvc.perform(get("/incrementItem/1").with(user("admin"))
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void testDecrementItem() throws Exception {
        mockMvc.perform(get("/decrementItem/1").with(user("admin"))
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());
    }
}
