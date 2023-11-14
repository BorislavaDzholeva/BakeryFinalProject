package BakeryProject.demo.service.impl;

import BakeryProject.demo.models.DTO.AdminAddCategoryDTO;
import BakeryProject.demo.models.entity.Category;
import BakeryProject.demo.repository.CategoryRepository;
import BakeryProject.demo.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public AdminAddCategoryDTO findCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return modelMapper.map(category, AdminAddCategoryDTO.class);
    }

    @Override
    public void updateCategory(AdminAddCategoryDTO addCategoryDTO) {
        Category category = categoryRepository.findById(addCategoryDTO.getId()).orElse(null);
        if(category != null){
            category.setName(addCategoryDTO.getName());
//            modelMapper.map(category, AddCategoryDTO.class);
            categoryRepository.save(category);
        }

    }
    @Override
    public void removeCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);

    }

}
