package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.AddProductDTO;
import BakeryProject.demo.models.entity.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAll();
    void addProduct(Product product);
    AddProductDTO findProductById(Long id);

    void removeProductById(Long id);

    void updateProduct(AddProductDTO addProductDTO);
}
