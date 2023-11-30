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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.util.MimeTypeUtils.IMAGE_PNG_VALUE;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AdminProductControllerIT {

    @Autowired
    MockMvc mockMvc;


    @Test
    void testProductAll() throws Exception {
        this.mockMvc.perform(get("/admin/products/").with(user("admin").roles("Administrator"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/products"));
    }

    @Test
    void testProductAddGet() throws Exception {
        this.mockMvc.perform(get("/admin/products/add/").with(user("admin").roles("Administrator"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/add_product"));

    }


    @Test
    void testAddProductWithAvailabilityAlways() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("image", "test-image.png", IMAGE_PNG_VALUE, new byte[200]);
        this.mockMvc.perform(multipart("/admin/products/add/").file(imageFile).with(user("admin").roles("Administrator"))
                        .param("name", "TestProduct2")
                        .param("price", "2.00")
                        .param("description", "TestProduct1 Description")
                        .param("weight", "200")
                        .param("allergens", "TestAllergen1, TestAllergen2")
                        .param("ingredients", "TestIngredient1, TestIngredient2")
                        .param("availability", "Always")
                        .param("category", "1")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/products/"));
    }
    @Test
    void testAddProductWithAvailabilityWeekend() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("image", "test-image.png", IMAGE_PNG_VALUE, new byte[200]);
        this.mockMvc.perform(multipart("/admin/products/add/").file(imageFile).with(user("admin").roles("Administrator"))
                        .param("name", "TestProduct2")
                        .param("price", "2.00")
                        .param("description", "TestProduct1 Description")
                        .param("weight", "200")
                        .param("allergens", "TestAllergen1, TestAllergen2")
                        .param("ingredients", "TestIngredient1, TestIngredient2")
                        .param("availability", "Weekend")
                        .param("category", "1")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/products/"));
    }

    @Test
    void testAddProductWithInvalidData() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("image", "test-image.png", IMAGE_PNG_VALUE, new byte[200]);
        this.mockMvc.perform(multipart("/admin/products/add/").file(imageFile).with(user("admin").roles("Administrator"))
                        .param("name", "")
                        .param("price", "2.00")
                        .param("description", "TestProduct1 Description")
                        .param("weight", "200")
                        .param("allergens", "TestAllergen1, TestAllergen2")
                        .param("ingredients", "TestIngredient1, TestIngredient2")
                        .param("availability", "Always")
                        .param("category", "1")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/products/add/"));
    }

    @Test
    void testProductEditGet() throws Exception {
        this.mockMvc.perform(get("/admin/products/edit/1").with(user("admin").roles("Administrator"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("/admin/edit_product"));

    }


    @Test
    void testProductEditConfirm() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("image", "test-image.png", IMAGE_PNG_VALUE, new byte[200]);
        this.mockMvc.perform(multipart("/admin/products/edit/").file(imageFile).with(user("admin").roles("Administrator"))
                        .param("id", "1")
                        .param("name", "TestProduct")
                        .param("price", "2.00")
                        .param("description", "TestProduct Description")
                        .param("weight", "200")
                        .param("allergens", "TestAllergen1")
                        .param("ingredients", "TestIngredient1, TestIngredient2")
                        .param("availability", "Always")
                        .param("category", "1")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/products/"));

    }

    @Test
    void testProductEditWithInvalidData() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("image", "test-image.png", IMAGE_PNG_VALUE, new byte[200]);
        this.mockMvc.perform(multipart("/admin/products/edit/").file(imageFile).with(user("admin").roles("Administrator"))
                        .param("id", "1")
                        .param("name", "")
                        .param("price", "2.00")
                        .param("description", "TestProduct Description")
                        .param("weight", "200")
                        .param("allergens", "TestAllergen1, TestAllergen2")
                        .param("ingredients", "TestIngredient1, TestIngredient2")
                        .param("availability", "Always")
                        .param("category", "1")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/products/edit/1"));

    }

    @Test
    void testRemoveProduct() throws Exception {
        this.mockMvc.perform(get("/admin/products/removeProduct/1").with(user("admin").roles("Administrator"))
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/products/"));
    }

    @Test
    void testRemoveCategory() throws Exception {
        this.mockMvc.perform(get("/admin/category/removeCategory/1").with(user("admin").roles("Administrator"))
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/category/"));

    }




}
