package co.istad.ecommerce.service.serviceImpl;

import co.istad.ecommerce.domain.Category;
import co.istad.ecommerce.dto.CategoryResponse;
import co.istad.ecommerce.dto.CreateCategoryRequest;
import co.istad.ecommerce.exception.AppException;
import co.istad.ecommerce.exception.ResourceNotFoundException;
import co.istad.ecommerce.mapper.CategoryMapper;
import co.istad.ecommerce.repository.CategoryRepository;
import co.istad.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    public final CategoryRepository categoryRepository;
    public final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createNew(CreateCategoryRequest createCategoryRequest) {

        log.info("CreateNew {} ", createCategoryRequest );



//        validate category name
        boolean isExisting = categoryRepository
                .existsByName(createCategoryRequest.name());
        if (isExisting)
            throw  new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Caegory has already been used"
            );
        Category parentCategory = null;
        CategoryResponse parentCategoryResponse = null;

// validation parent category
        if (createCategoryRequest.parentCategoryId() != null){
            parentCategory =  categoryRepository.findById(createCategoryRequest.parentCategoryId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Parent categoy has not  been found"
                    ));



        }

        Category category = categoryMapper
                .mapCreateCategoryRequestToCategory(createCategoryRequest);


//        sysstem generate data
        category.setIsDeleted(false);
        category.setParentCategory(parentCategory);

//        Insert if primary key is null
//        update if primary key has value
        category = categoryRepository.save(category);

        return  categoryMapper.mapCategoryToCategoryResponse(category);

//        return CategoryResponse.builder()
//                .id(category.getId())
//                .name(category.getName())
//                .description(category.getDescription())
//                .icon(category.getIcon())
//                .isDeleted(category.getIsDeleted())
//                .parentCategory(parentCategoryResponse)
//                .build();
    }

    @Override
    public Page<CategoryResponse> getAllCategory(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage.map(categoryMapper::mapCategoryToCategoryResponse);

    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return categoryMapper.mapCategoryToCategoryResponse(category);
    }

    @Override
    public CategoryResponse deleteCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.delete(category);
        return categoryMapper.mapCategoryToCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getSubcategories(Integer id) {
        List<Category> subcategories = categoryRepository.getCategoryByParentCategoryId(id);
        return subcategories.stream().map(categoryMapper::mapCategoryToCategoryResponse).toList();
    }

    @Override
    public CategoryResponse updateCategoryById(Integer id, CreateCategoryRequest createCategoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        category.setName(createCategoryRequest.name());
        category.setIcon(createCategoryRequest.icon());
        category.setDescription(createCategoryRequest.description());

        return categoryMapper.mapCategoryToCategoryResponse(categoryRepository.save(category));
    }


}


//    request to domain , domain to response



