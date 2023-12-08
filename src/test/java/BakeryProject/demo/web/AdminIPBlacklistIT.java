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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AdminIPBlacklistIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    void testIPBlacklistAll() throws Exception {
        this.mockMvc.perform(get("/admin/ip_black_list/").with(user("admin").roles("Administrator"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/ip_black_list"));
    }

    @Test
    void testIPBlacklistAdd() throws Exception {
        this.mockMvc.perform(get("/admin/ip_black_list/add/").with(user("admin").roles("Administrator"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/add_ip_to_black_list"));
    }

    @Test
    void testIPBlacklistAddConfirm() throws Exception {
        this.mockMvc.perform(post("/admin/ip_black_list/add/").with(user("admin").roles("Administrator"))
                        .param("ip", "192.168.10.11")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/ip_black_list/"));
    }

    @Test
    void testIPBlacklistAddConfirmInvalidIP() throws Exception {
        this.mockMvc.perform(post("/admin/ip_black_list/add/").with(user("admin").roles("Administrator"))
                        .param("ip", "asd")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/ip_black_list/add/"));
    }



    @Test
    void testIPBlacklistRemove() throws Exception {
        this.mockMvc.perform(get("/admin/ip_black_list/removeIP/{id}", 1).with(user("admin").roles("Administrator"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/ip_black_list/"));
    }

    @Test
    void testIPBlacklistRemoveInvalidId() throws Exception {
        this.mockMvc.perform(get("/admin/ip_black_list/removeIP/{id}", 100).with(user("admin").roles("Administrator"))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/ip_black_list/"));
    }

}
