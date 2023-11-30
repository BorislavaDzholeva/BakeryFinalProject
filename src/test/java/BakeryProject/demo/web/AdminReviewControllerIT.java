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
public class AdminReviewControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testAll() throws Exception {
        this.mockMvc.perform(get("/admin/review/").with(user("admin").roles("Administrator")))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/review"));
    }

    @Test
    void testRemoveReview() throws Exception {
        this.mockMvc.perform(get("/admin/review/removeReview/1").with(user("admin").roles("Administrator")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/review/"));
    }

    @Test
    void testApproveReview() throws Exception {
        this.mockMvc.perform(get("/admin/review/approve/1").with(user("admin").roles("Administrator")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/review/"));
    }

}
