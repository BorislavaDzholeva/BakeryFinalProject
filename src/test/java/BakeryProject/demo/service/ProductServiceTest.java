package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.AdminAddProductDTO;
import BakeryProject.demo.models.entity.Category;
import BakeryProject.demo.models.entity.Product;
import BakeryProject.demo.models.enums.AvailabilityEnum;
import BakeryProject.demo.repository.ProductRepository;
import BakeryProject.demo.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository mockProductRepository;
    @Mock
    private CategoryService mockCategoryService;
    @Spy
    private ModelMapper modelMapper;
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;
    private ProductServiceImpl serviceToTest;
    private Product testProduct;
    private Category testCategory;
    private AdminAddProductDTO testAdminAddProductDTO;

    @BeforeEach
    void setUp() {
        serviceToTest = new ProductServiceImpl(mockProductRepository, mockCategoryService, modelMapper);
        testCategory = new Category() {
            {
                setId(1L);
                setName("test name");
            }
        };

        testProduct = new Product() {
            {
                setId(1L);
                setName("test name");
                setPrice(BigDecimal.valueOf(1.0));
                setWeight(1);
                setCategory(null);
                setProductImage("test url");
                setDescription("test description");
                setAllergens("test allergens");
                setIngredients("test ingredients");
                setAvailability(AvailabilityEnum.valueOf("Always"));
                setCategory(testCategory);

            }
        };
        testAdminAddProductDTO = new AdminAddProductDTO() {
            {
                setId(1L);
                setName("test name");
                setPrice(BigDecimal.valueOf(1.0));
                setWeight(1);
                setCategory(null);
                setProductImage("test url");
                setDescription("test description");
                setAllergens("test allergens");
                setIngredients("test ingredients");
                setAvailability(AvailabilityEnum.valueOf("Always"));
                setAvailability(AvailabilityEnum.valueOf("Weekend"));
                setCategory(testCategory);

            }
        };


    }

    @Test
    void testCreateProduct() {
        when(mockCategoryService.findCategoryByName(testProduct.getCategory().getName())).thenReturn(testProduct.getCategory());
        serviceToTest.addProduct(testProduct);
        Mockito.verify(mockProductRepository).save(productArgumentCaptor.capture());
        Product capturedProduct = productArgumentCaptor.getValue();
        Assertions.assertEquals(testProduct.getName(), capturedProduct.getName());
        Assertions.assertEquals(testProduct.getPrice(), capturedProduct.getPrice());
        Assertions.assertEquals(testProduct.getWeight(), capturedProduct.getWeight());
        Assertions.assertEquals(testProduct.getCategory(), capturedProduct.getCategory());
        Assertions.assertEquals(testProduct.getProductImage(), capturedProduct.getProductImage());
        Assertions.assertEquals(testProduct.getDescription(), capturedProduct.getDescription());
        Assertions.assertEquals(testProduct.getAllergens(), capturedProduct.getAllergens());
        Assertions.assertEquals(testProduct.getIngredients(), capturedProduct.getIngredients());
        Assertions.assertEquals(testProduct.getAvailability(), capturedProduct.getAvailability());


    }

    @Test
    void testUpdateProduct() {
        when(mockProductRepository.findById(testAdminAddProductDTO.getId())).thenReturn(Optional.of(testProduct));
        serviceToTest.updateProduct(testAdminAddProductDTO);
        Mockito.verify(mockProductRepository).save(productArgumentCaptor.capture());
        Assertions.assertEquals(testAdminAddProductDTO.getName(), productArgumentCaptor.getValue().getName());
    }

    @Test
    void testGetProductByCategoryId() {
        when(mockProductRepository.findAllByCategoryId(testCategory.getId())).thenReturn(List.of(testProduct));
        serviceToTest.findAllProductsByCategoryId(testCategory.getId());
        Mockito.verify(mockProductRepository).findAllByCategoryId(testCategory.getId());
        Assertions.assertEquals(1, serviceToTest.findAllProductsByCategoryId(testCategory.getId()).size());
    }

    @Test
    void testGetAllProducts() {
        when(mockProductRepository.findAll()).thenReturn(List.of(testProduct));
        serviceToTest.getAll();
        Mockito.verify(mockProductRepository).findAll();
        Assertions.assertEquals(1, serviceToTest.getAll().size());

    }

    @Test
    void testFindProductById() {
        when(mockProductRepository.findById(testProduct.getId())).thenReturn(Optional.of(testProduct));
        serviceToTest.findProductById(testProduct.getId());
        Mockito.verify(mockProductRepository).findById(testProduct.getId());
        Assertions.assertEquals(testProduct.getId(), serviceToTest.findProductById(testProduct.getId()).getId());

    }

    @Test
    void testRemoveProductById() {
        serviceToTest.removeProductById(testProduct.getId());
        Mockito.verify(mockProductRepository).deleteById(testProduct.getId());
        Assertions.assertTrue(mockProductRepository.findById(testProduct.getId()).isEmpty());
    }

    @Test
    void testFindById() {
        when(mockProductRepository.findById(testProduct.getId())).thenReturn(Optional.of(testProduct));
        serviceToTest.findById(testProduct.getId());
        Mockito.verify(mockProductRepository).findById(testProduct.getId());
        Assertions.assertEquals(testProduct.getId(), serviceToTest.findById(testProduct.getId()).getId());
    }

    @Test
    void testAddProduct() {
        when(mockCategoryService.findCategoryByName(testProduct.getCategory().getName())).thenReturn(testProduct.getCategory());
        serviceToTest.addProduct(testProduct);
        Mockito.verify(mockProductRepository).save(productArgumentCaptor.capture());
        Product capturedProduct = productArgumentCaptor.getValue();
        Assertions.assertEquals(testProduct.getName(), capturedProduct.getName());
        Assertions.assertEquals(testProduct.getPrice(), capturedProduct.getPrice());
        Assertions.assertEquals(testProduct.getWeight(), capturedProduct.getWeight());
        Assertions.assertEquals(testProduct.getCategory(), capturedProduct.getCategory());
        Assertions.assertEquals(testProduct.getProductImage(), capturedProduct.getProductImage());
        Assertions.assertEquals(testProduct.getDescription(), capturedProduct.getDescription());
        Assertions.assertEquals(testProduct.getAllergens(), capturedProduct.getAllergens());
        Assertions.assertEquals(testProduct.getIngredients(), capturedProduct.getIngredients());
        Assertions.assertEquals(testProduct.getAvailability(), capturedProduct.getAvailability());
    }

    @Test
    void testUploadProductImage() {
        final MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test file name");
        Assertions.assertThrows(NullPointerException.class, () -> {
            serviceToTest.uploadProductImage(mockFile);
        });

    }
}
