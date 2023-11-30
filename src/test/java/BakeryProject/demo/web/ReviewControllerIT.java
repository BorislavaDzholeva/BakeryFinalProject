package BakeryProject.demo.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerIT {

    @Autowired
    MockMvc mockMvc;


    @Test
    void testCreateReviewGet() throws Exception {
        this.mockMvc.perform(get("/reviews/create").with(user("admin")))
                .andExpect(status().isOk())
                .andExpect(view().name("create-review"));
    }

    @Test
    void testCreateReviewConfirm() throws Exception {
        this.mockMvc.perform(post("/reviews/create").with(user("admin"))
                        .param("message", "Test")
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    void testCreateReviewConfirmWithInvalidData() throws Exception {
        this.mockMvc.perform(post("/reviews/create").with(user("admin"))
                        .param("message", "Te")
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/reviews/create"));
    }


}
