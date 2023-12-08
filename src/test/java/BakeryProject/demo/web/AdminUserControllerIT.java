package BakeryProject.demo.web;

import jakarta.transaction.Transactional;
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
@Transactional
public class AdminUserControllerIT {
    @Autowired
    MockMvc mockMvc;


    @Test
    void testUserAll() throws Exception {
        this.mockMvc.perform(get("/admin/").with(user("admin").roles("Administrator")))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/admin"));
    }

    @Test
    void testUserAddGet() throws Exception {
        this.mockMvc.perform(get("/admin/user/add/").with(user("admin").roles("Administrator")))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/add_user"));

    }

    @Test
    void testUserAdd() throws Exception {
        this.mockMvc.perform(post("/admin/user/add/").with(user("admin").roles("Administrator"))
                        .param("username", "TestUser")
                        .param("password", "123456")
                        .param("email", "testUser@abv.bg")
                        .param("firstName", "Test")
                        .param("lastName", "User")
                        .param("role", "User")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/"));

    }

    @Test
    void testAddUserWithInvalidData() throws Exception {
        this.mockMvc.perform(post("/admin/user/add/").with(user("admin").roles("Administrator"))
                        .param("username", "")
                        .param("password", "123456")
                        .param("email", "testUser@abv.bg")
                        .param("firstName", "Test")
                        .param("lastName", "User")
                        .param("role", "User")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/user/add/"));
    }

    @Test
    void testUserEditGet() throws Exception {
        this.mockMvc.perform(get("/admin/user/edit/1").with(user("admin").roles("Administrator")))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/edit_user"));
    }

    @Test
    void userEditConfirm() throws Exception {
        this.mockMvc.perform(post("/admin/user/edit/").with(user("admin").roles("Administrator"))
                        .param("id", "1")
                        .param("username", "TestUser")
                        .param("password", "123456")
                        .param("email", "test@abv.bg")
                        .param("firstName", "Test")
                        .param("lastName", "User")
                        .param("role", "User")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/"));
    }


    @Test
    void testEditUserWithInvalidData() throws Exception {
        this.mockMvc.perform(post("/admin/user/edit/").with(user("admin").roles("Administrator"))
                        .param("id", "1")
                        .param("username", "")
                        .param("password", "123456")
                        .param("email", "test@abv.bg")
                        .param("firstName", "Test")
                        .param("lastName", "User")
                        .param("role", "User")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/user/edit/1"));
    }

    @Test
    void testRemoveUser() throws Exception {
        this.mockMvc.perform(get("/admin/removeUser/1").with(user("admin").roles("Administrator"))
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/"));
    }
}
