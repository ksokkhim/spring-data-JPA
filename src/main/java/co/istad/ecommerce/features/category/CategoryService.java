package co.istad.ecommerce.features.category;


import co.istad.ecommerce.features.category.dto.CategoryResponse;
import co.istad.ecommerce.features.category.dto.CreateCategoryRequest;
import co.istad.ecommerce.features.dto.RequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CategoryService {

    CategoryResponse createNew(CreateCategoryRequest createCategoryRequest);

    Page<CategoryResponse> getAllCategory(int pageNumber, int pageSize);

    CategoryResponse getCategoryById(Integer id);

    CategoryResponse deleteCategoryById(Integer id);

    List<CategoryResponse> getSubcategories(Integer id);

    CategoryResponse updateCategoryById(Integer id, CreateCategoryRequest createCategoryRequest);

//    List<CategoryResponse> findByCategoryName(String name);

    List<CategoryResponse> findByCategoryNameContains(String name);

//    filter specification
    Page<CategoryResponse> findByCriteria(RequestDto requestDto, Pageable pageable);
}
