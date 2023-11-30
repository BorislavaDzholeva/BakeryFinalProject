package BakeryProject.demo.web;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.util.MimeTypeUtils.IMAGE_PNG_VALUE;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdminCategoryControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testCategoryAll() throws Exception {
        this.mockMvc.perform(get("/admin/category/").with(user("admin").roles("Administrator")))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/category"));
    }

    @Test
    void testCategoryAddGet() throws Exception {
        this.mockMvc.perform(get("/admin/category/add/").with(user("admin").roles("Administrator")))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/add_category"));

    }


    @Test
    void testCategoryAdd() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("image", "test-image.png", IMAGE_PNG_VALUE, new byte[200]);
        this.mockMvc.perform(multipart("/admin/category/add/").file(imageFile).with(user("admin").roles("Administrator"))
                        .param("name", "TestCategory2")
                        .param("description", "TestCategory1 Description")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/category/"));

    }

    @Test
    void testAddCategoryWithInvalidData() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("image", "test-image.png", IMAGE_PNG_VALUE, new byte[200]);
        this.mockMvc.perform(multipart("/admin/category/add/").file(imageFile).with(user("admin").roles("Administrator"))
                        .param("name", "")
                        .param("description", "TestCategory1 Description")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/category/add/"));

    }


    @Test
    void testCategoryEditGet() throws Exception {
        this.mockMvc.perform(get("/admin/category/edit/1").with(user("admin").roles("Administrator")))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/edit_category"));

    }
    @Test
    void categoryEditConfirm() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("image", "test-image.png", IMAGE_PNG_VALUE, new byte[200]);
        this.mockMvc.perform(multipart("/admin/category/edit/").file(imageFile).with(user("admin").roles("Administrator"))
                        .param("id", "1")
                        .param("name", "TestCategory")
                        .param("description", "TestCategory Description")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/category/"));

    }



   @Test
    void testRemoveCategory() throws Exception {
        this.mockMvc.perform(get("/admin/category/removeCategory/1").with(user("admin").roles("Administrator"))
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/category/"));

    }


    @Test
    void testEditCategoryWithInvalidData() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("image", "test-image.png", IMAGE_PNG_VALUE, new byte[200]);
        this.mockMvc.perform(multipart("/admin/category/edit/").file(imageFile).with(user("admin").roles("Administrator"))
                        .param("id", "1")
                        .param("name", "")
                        .param("description", "TestCategory Description")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/category/edit/1"));

    }





}