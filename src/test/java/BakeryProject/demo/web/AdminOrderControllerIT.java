package BakeryProject.demo.web;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AdminOrderControllerIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    void testOrderAll() throws Exception {
        this.mockMvc.perform(get("/admin/orders/").with(user("admin").roles("Administrator")))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/orders"));
    }

    @Test
    void testOrderStatus() throws Exception {
        this.mockMvc.perform(get("/admin/orders/status/1").with(user("admin").roles("Administrator")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/orders/"));
    }
}
