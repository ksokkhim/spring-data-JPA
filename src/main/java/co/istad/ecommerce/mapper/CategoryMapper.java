package co.istad.ecommerce.mapper;

import co.istad.ecommerce.domain.Category;
import co.istad.ecommerce.dto.CategoryResponse;
import co.istad.ecommerce.dto.CreateCategoryRequest;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

//    return type = target
//    parameter = source
    Category mapCreateCategoryRequestToCategory(CreateCategoryRequest createCategoryRequest) ;


    CategoryResponse mapCategoryToCategoryResponse(Category category);

}
