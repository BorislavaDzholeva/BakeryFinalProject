package BakeryProject.demo.service.impl;

import BakeryProject.demo.models.entity.Product;
import BakeryProject.demo.repository.ProductRepository;
import BakeryProject.demo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
