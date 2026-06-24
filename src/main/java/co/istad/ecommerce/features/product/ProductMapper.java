package co.istad.ecommerce.features.product;

import co.istad.ecommerce.features.category.dto.CreateCategoryRequest;
import co.istad.ecommerce.features.product.dto.CreateProductRequest;
import co.istad.ecommerce.features.product.dto.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

//    target is return type
//    parameter is response

    Product mapCreateProductRequestToProduct(CreateProductRequest createProductRequest);
    ProductResponse mapProductToProductResponse(Product product);
}
