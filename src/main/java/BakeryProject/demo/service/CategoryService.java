package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.AdminAddCategoryDTO;
import BakeryProject.demo.models.entity.Category;
import BakeryProject.demo.models.view.CategoryView;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    void addCategory(Category category);

    List<CategoryView> getAllCategories();
    AdminAddCategoryDTO findCategoryById(Long id);
    void updateCategory(AdminAddCategoryDTO addCategoryDTO);

    void removeCategoryById(Long id);
    Category findCategoryByName(String name);
    String uploadCategoryImage(MultipartFile file) throws IOException;
}
