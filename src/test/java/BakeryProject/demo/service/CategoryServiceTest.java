package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.AdminAddCategoryDTO;
import BakeryProject.demo.models.entity.Category;
import BakeryProject.demo.repository.CategoryRepository;
import BakeryProject.demo.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository mockCategoryRepository;
    @Spy
    private ModelMapper modelMapper;
    @Captor
    private ArgumentCaptor<Category> categoryArgumentCaptor;
    private CategoryServiceImpl serviceToTest;
    private Category testCategory;
    private AdminAddCategoryDTO testAdminAddCategoryDTO;
    private ResourceLoader resourceLoader;

    @BeforeEach
    void setUp() {
        serviceToTest = new CategoryServiceImpl(mockCategoryRepository, modelMapper, resourceLoader, "images");
        testCategory = new Category() {
            {
                setId(1L);
                setName("name");
                setImageUrl("image");
                setDescription("description");
            }
        };
        testAdminAddCategoryDTO = new AdminAddCategoryDTO() {
            {
                setId(1L);
                setName("name");
                setImageUrl("image");
                setDescription("description");
            }
        };
    }

    @Test
    void testAddCategory() {
        serviceToTest.addCategory(testCategory);
        Mockito.verify(mockCategoryRepository).save(categoryArgumentCaptor.capture());
        Category capturedCategory = categoryArgumentCaptor.getValue();
        Assertions.assertEquals(testCategory, capturedCategory);
        Assertions.assertEquals(testCategory.getId(), capturedCategory.getId());
        Assertions.assertEquals(testCategory.getName(), capturedCategory.getName());
        Assertions.assertEquals(testCategory.getImageUrl(), capturedCategory.getImageUrl());
        Assertions.assertEquals(testCategory.getDescription(), capturedCategory.getDescription());

    }

    @Test
    void testGetAllCategories() {
        when(mockCategoryRepository.findAll()).thenReturn(java.util.List.of(testCategory));
        serviceToTest.getAllCategories();
        Mockito.verify(mockCategoryRepository).findAll();
        Assertions.assertEquals(1, serviceToTest.getAllCategories().size());
    }

    @Test
    void testFindCategoryById() {
        when(mockCategoryRepository.findById(testCategory.getId())).thenReturn(Optional.of(testCategory));
        serviceToTest.findCategoryById(testCategory.getId());
        Mockito.verify(mockCategoryRepository).findById(testCategory.getId());
        Assertions.assertEquals(testCategory.getId(), serviceToTest.findCategoryById(testCategory.getId()).getId());

    }

    @Test
    void testUpdateCategory() {
        when(mockCategoryRepository.findById(testAdminAddCategoryDTO.getId())).thenReturn(Optional.of(testCategory));
        serviceToTest.updateCategory(testAdminAddCategoryDTO);
        Mockito.verify(mockCategoryRepository).save(categoryArgumentCaptor.capture());
        Assertions.assertEquals(testAdminAddCategoryDTO.getName(), categoryArgumentCaptor.getValue().getName());
        Assertions.assertEquals(testAdminAddCategoryDTO.getImageUrl(), categoryArgumentCaptor.getValue().getImageUrl());
        Assertions.assertEquals(testAdminAddCategoryDTO.getDescription(), categoryArgumentCaptor.getValue().getDescription());
        Assertions.assertEquals(testAdminAddCategoryDTO.getId(), categoryArgumentCaptor.getValue().getId());
    }

    @Test
    void testRemoveCategoryById() {
        serviceToTest.removeCategoryById(testCategory.getId());
        Mockito.verify(mockCategoryRepository).deleteById(testCategory.getId());
        Assertions.assertTrue(mockCategoryRepository.findById(testCategory.getId()).isEmpty());
    }

    @Test
    void testFindCategoryByName() {
        when(mockCategoryRepository.findCategoryByName(testCategory.getName())).thenReturn(testCategory);
        serviceToTest.findCategoryByName(testCategory.getName());
        Mockito.verify(mockCategoryRepository).findCategoryByName(testCategory.getName());
        Assertions.assertEquals(testCategory.getName(), serviceToTest.findCategoryByName(testCategory.getName()).getName());
    }

    @Test
    void testUploadCategoryImage() {
        final MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("test file name");
        Assertions.assertThrows(NullPointerException.class, () -> {
            serviceToTest.uploadCategoryImage(mockFile);
        });
    }


}



