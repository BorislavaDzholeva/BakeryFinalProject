package BakeryProject.demo.service.impl;

import BakeryProject.demo.models.DTO.AddProductDTO;
import BakeryProject.demo.models.entity.Product;
import BakeryProject.demo.repository.ProductRepository;
import BakeryProject.demo.service.CategoryService;
import BakeryProject.demo.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public void addProduct(Product product) {
        product.setCategory(categoryService.findCategoryByName(product.getCategory().getName()));
        productRepository.save(product);
    }

    @Override
    public AddProductDTO findProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return modelMapper.map(product, AddProductDTO.class);
    }

    @Override
    public void removeProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void updateProduct(AddProductDTO addProductDTO) {
        Product product = productRepository.findById(addProductDTO.getId()).orElse(null);
        if (product != null) {
            //modelMapper.map(product, AddProductDTO.class);
            product.setName(addProductDTO.getName());
            product.setAllergens(addProductDTO.getAllergens());
            product.setAvailability(addProductDTO.getAvailability());
            product.setDescription(addProductDTO.getDescription());
            product.setIngredients(addProductDTO.getIngredients());
            product.setPrice(addProductDTO.getPrice());
            product.setWeight(addProductDTO.getWeight());
            product.setCategory(addProductDTO.getCategory());
            productRepository.save(product);
        }

    }
}



