package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.AdminAddCategoryDTO;
import BakeryProject.demo.models.entity.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(Category category);

    List<Category> getAllCategories();
    AdminAddCategoryDTO findCategoryById(Long id);
    void updateCategory(AdminAddCategoryDTO addCategoryDTO);

    void removeCategoryById(Long id);
    Category findCategoryByName(String name);

}
