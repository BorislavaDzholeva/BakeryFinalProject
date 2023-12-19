package BakeryProject.demo.service.impl;

import BakeryProject.demo.exception.ObjectNotFoundException;
import BakeryProject.demo.models.DTO.AdminAddProductDTO;
import BakeryProject.demo.models.entity.Product;
import BakeryProject.demo.repository.ProductRepository;
import BakeryProject.demo.service.CategoryService;
import BakeryProject.demo.service.ProductService;
import BakeryProject.demo.models.view.ProductsView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service

public class ProductServiceImpl implements ProductService {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images/";
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private String containerName;

    static final String BLOB_RESOURCE_PATTERN = "azure-blob://%s/%s";
    static final String PUBLIC_URL_PATTERN = "https://borislavabakeryimages.blob.core.windows.net/images/%s";

    private final ResourceLoader resourceLoader;


    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ModelMapper modelMapper, @Qualifier("azureStorageBlobProtocolResolver") ResourceLoader resourceLoader,@Value("${spring.cloud.azure.storage.blob.container-name}") String containerName) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.resourceLoader = resourceLoader;
        this.containerName = containerName;
    }

    @Override
    public List<ProductsView> getAll() {
        return productRepository.findAll().stream().
                map(product -> modelMapper.map(product, ProductsView.class))
                .toList();
    }

    @Override
    public void addProduct(Product product) {
        product.setCategory(categoryService.findCategoryByName(product.getCategory().getName()));
        productRepository.save(product);
    }

    @Override
    public AdminAddProductDTO findProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Not found"));
        return modelMapper.map(product, AdminAddProductDTO.class);
    }

    @Override
    public String uploadProductImage(MultipartFile file) throws IOException {

        Resource resource = resourceLoader.getResource(String.format(BLOB_RESOURCE_PATTERN, this.containerName, file.getOriginalFilename()));
        try (OutputStream os = ((WritableResource) resource).getOutputStream()) {
            os.write(file.getBytes());
        }
        return String.format(PUBLIC_URL_PATTERN, file.getOriginalFilename());

    }

    @Override
    public void removeProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void updateProduct(AdminAddProductDTO addProductDTO) {
        Product product = productRepository.findById(addProductDTO.getId()).orElseThrow(() -> new ObjectNotFoundException("Not found"));
        product.setName(addProductDTO.getName());
        product.setAllergens(addProductDTO.getAllergens());
        product.setAvailability(addProductDTO.getAvailability());
        product.setDescription(addProductDTO.getDescription());
        product.setIngredients(addProductDTO.getIngredients());
        product.setPrice(addProductDTO.getPrice());
        product.setWeight(addProductDTO.getWeight());
        product.setCategory(addProductDTO.getCategory());
        if (addProductDTO.getProductImage() != null) {
            product.setProductImage(addProductDTO.getProductImage());
        }
        productRepository.save(product);


    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Not found"));
    }

    @Override
    public List<Product> findAllProductsByCategoryId(Long id) {
        List<Product> allByCategoryId = productRepository.findAllByCategoryId(id);
        if(allByCategoryId == null || allByCategoryId.isEmpty()){
            throw new ObjectNotFoundException("Not found");
        }
        return allByCategoryId;
    }
}



