package BakeryProject.demo.service;

import BakeryProject.demo.models.entity.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(Category category);

    List<Category> getAllCategories();

}
