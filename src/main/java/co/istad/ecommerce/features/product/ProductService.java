package co.istad.ecommerce.features.product;

import co.istad.ecommerce.features.product.dto.CreateProductRequest;
import co.istad.ecommerce.features.product.dto.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {

    /**
     *
     * find product by  pagination
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<ProductResponse> findAll(int pageNumber, int pageSize);

    /**
     * create a new product
     * @param createProductReques is requesting data for creating product
     * @return {@link ProductResponse}
     * @author name
     * @since 23-june-2026
     */
    ProductResponse createNew(CreateProductRequest createProductReques);
}

