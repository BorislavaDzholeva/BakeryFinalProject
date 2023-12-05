package BakeryProject.demo.service.impl;

import BakeryProject.demo.models.DTO.AdminAddCategoryDTO;
import BakeryProject.demo.models.entity.Category;
import BakeryProject.demo.models.view.CategoryView;
import BakeryProject.demo.repository.CategoryRepository;
import BakeryProject.demo.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images/";

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
    public List<CategoryView> getAllCategories() {
        return categoryRepository.
                findAll().stream().map(category -> modelMapper.map(category, CategoryView.class))
                .collect(Collectors.toList());
    }

    @Override
    public AdminAddCategoryDTO findCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return modelMapper.map(category, AdminAddCategoryDTO.class);
    }

    @Override
    public void updateCategory(AdminAddCategoryDTO addCategoryDTO) {
        Category category = categoryRepository.findById(addCategoryDTO.getId()).orElseThrow();
        category.setName(addCategoryDTO.getName());
        if (addCategoryDTO.getImageUrl() != null) {
            category.setImageUrl(addCategoryDTO.getImageUrl());
        }
        category.setDescription(addCategoryDTO.getDescription());
        categoryRepository.save(category);


    }

    @Override
    public void removeCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);

    }

    @Override
    public String uploadCategoryImage(MultipartFile file) throws IOException {
        StringBuilder fileName = new StringBuilder();
        Path fileNameAndPath = Path.of(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileName.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        return "/images/" + file.getOriginalFilename();
    }

}
