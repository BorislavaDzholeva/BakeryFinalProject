package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.AdminAddProductDTO;
import BakeryProject.demo.models.entity.Product;
import BakeryProject.demo.models.view.ProductsView;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductsView> getAll();
    void addProduct(Product product);
    AdminAddProductDTO findProductById(Long id);

    String uploadProductImage(MultipartFile file) throws IOException;

    void removeProductById(Long id);

    void updateProduct(AdminAddProductDTO addProductDTO);

    Product findById(Long id);

    List<Product> findAllProductsByCategoryId(Long id);

}
