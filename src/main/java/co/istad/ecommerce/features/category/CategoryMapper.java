package co.istad.ecommerce.features.category;

import co.istad.ecommerce.features.category.dto.CategoryResponse;
import co.istad.ecommerce.features.category.dto.CreateCategoryRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

//    return type = target
//    parameter = source
    Category mapCreateCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest) ;

    CategoryResponse mapCategoryToCategoryResponse(Category category);

}
