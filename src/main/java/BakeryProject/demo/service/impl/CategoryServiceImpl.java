package BakeryProject.demo.service.impl;

import BakeryProject.demo.exception.ObjectNotFoundException;
import BakeryProject.demo.models.DTO.AdminAddCategoryDTO;
import BakeryProject.demo.models.entity.Category;
import BakeryProject.demo.models.view.CategoryView;
import BakeryProject.demo.repository.CategoryRepository;
import BakeryProject.demo.service.CategoryService;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ResourceLoader resourceLoader;

    private String containerName;
    static final String BLOB_RESOURCE_PATTERN = "azure-blob://%s/%s";
    static final String PUBLIC_URL_PATTERN = "https://borislavabakeryimages.blob.core.windows.net/images/%s";

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, @Qualifier("azureStorageBlobProtocolResolver") ResourceLoader resourceLoader,    @Value("${spring.cloud.azure.storage.blob.container-name}") String containerName) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.resourceLoader = resourceLoader;
        this.containerName = containerName;
    }

    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryView> getAllCategories() {
        return categoryRepository.findAll().stream().map(category -> modelMapper.map(category, CategoryView.class)).collect(Collectors.toList());
    }

    @Override
    public AdminAddCategoryDTO findCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Not found"));
        return modelMapper.map(category, AdminAddCategoryDTO.class);
    }

    @Override
    public void updateCategory(AdminAddCategoryDTO addCategoryDTO) {
        Category category = categoryRepository.findById(addCategoryDTO.getId()).orElseThrow(() -> new ObjectNotFoundException("Not found"));
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
        Resource resource = resourceLoader.getResource(String.format(BLOB_RESOURCE_PATTERN, this.containerName, file.getOriginalFilename()));
        try (OutputStream os = ((WritableResource) resource).getOutputStream()) {
            os.write(file.getBytes());
        }
        return String.format(PUBLIC_URL_PATTERN, file.getOriginalFilename());
    }

}
