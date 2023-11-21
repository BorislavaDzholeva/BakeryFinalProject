package BakeryProject.demo.service.impl;

import BakeryProject.demo.models.DTO.AdminAddProductDTO;
import BakeryProject.demo.models.entity.Product;
import BakeryProject.demo.repository.ProductRepository;
import BakeryProject.demo.service.CategoryService;
import BakeryProject.demo.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service

public class ProductServiceImpl implements ProductService {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images/";

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
    public AdminAddProductDTO findProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return modelMapper.map(product, AdminAddProductDTO.class);
    }

    @Override
    public String uploadProductImage(MultipartFile file) throws IOException {
        StringBuilder fileName = new StringBuilder();
        Path fileNameAndPath = Path.of(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileName.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        return "/images/" + file.getOriginalFilename();
    }

    @Override
    public void removeProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void updateProduct(AdminAddProductDTO addProductDTO) {
        Product product = productRepository.findById(addProductDTO.getId()).orElse(null);
        if (product != null) {
            product.setName(addProductDTO.getName());
            product.setAllergens(addProductDTO.getAllergens());
            product.setAvailability(addProductDTO.getAvailability());
            product.setDescription(addProductDTO.getDescription());
            product.setIngredients(addProductDTO.getIngredients());
            product.setPrice(addProductDTO.getPrice());
            product.setWeight(addProductDTO.getWeight());
            product.setCategory(addProductDTO.getCategory());
            product.setProductImage(addProductDTO.getProductImage());
            productRepository.save(product);
        }

    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> findAllProductsByCategoryId(Long id) {
        return productRepository.findAllByCategoryId(id);
    }
}



