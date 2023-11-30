package BakeryProject.demo.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIT {
    @Autowired
    MockMvc mockMvc;


    @Test
    void testProductsAll() throws Exception {
        this.mockMvc.perform(get("/products/"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"));
    }

    @Test
    void testSingleProductPage() throws Exception {
        this.mockMvc.perform(get("/products/single-product/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("single-product"));
    }

    @Test
    void testProductsByCategory() throws Exception {
        this.mockMvc.perform(get("/products/productsByCategory/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"));
    }
}
