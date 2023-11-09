package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.AddCategoryDTO;
import BakeryProject.demo.models.DTO.AddProductDTO;
import BakeryProject.demo.models.entity.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(Category category);

    List<Category> getAllCategories();
    AddCategoryDTO findCategoryById(Long id);
    void updateCategory(AddCategoryDTO addCategoryDTO);

    void removeCategoryById(Long id);
    Category findCategoryByName(String name);

}
