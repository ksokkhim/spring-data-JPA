package co.istad.ecommerce.service;

import co.istad.ecommerce.domain.Category;
import co.istad.ecommerce.dto.CategoryResponse;
import co.istad.ecommerce.dto.CreateCategoryRequest;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CategoryService {
    CategoryResponse createNew(CreateCategoryRequest createCategoryRequest);

    public Page<CategoryResponse> getAllCategory(int pageNumber, int pageSize);

    CategoryResponse getCategoryById(Integer id);

    CategoryResponse deleteCategoryById(Integer id);

    List<CategoryResponse> getSubcategories(Integer id);

    CategoryResponse updateCategoryById(Integer id, CreateCategoryRequest createCategoryRequest);

}
