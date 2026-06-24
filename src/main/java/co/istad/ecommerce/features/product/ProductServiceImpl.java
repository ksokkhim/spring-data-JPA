package co.istad.ecommerce.features.product;

import co.istad.ecommerce.features.category.Category;
import co.istad.ecommerce.features.category.CategoryRepository;
import co.istad.ecommerce.features.product.dto.CreateProductRequest;
import co.istad.ecommerce.features.product.dto.ProductResponse;
import co.istad.ecommerce.utils.GenerateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;


    @Override
    public Page<ProductResponse> findAll(int pageNumber, int pageSize) {

        Sort sortById = Sort.by(Sort.Direction.DESC, "id");

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        Page<Product> products = productRepository
                .findAll(pageRequest);
        return products.map(productMapper::mapProductToProductResponse);
    }

    @Override
    public ProductResponse createNew(CreateProductRequest createProductRequest) {

//   validate product name
if (productRepository.existsByName(createProductRequest.name())) {
    throw new ResponseStatusException(HttpStatus.CONFLICT, "Product name has already been used");
}

//validate category ID
        Category category = categoryRepository.findById(createProductRequest.categoryId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category has not found"));

// Transfer data from DTO to Model
        Product product = productMapper
                .mapCreateProductRequestToProduct(createProductRequest);

//        set generated system data
        product.setCategory(category);
        product.setCode(GenerateUtils.generateProductCode());
        product.setSlug(GenerateUtils.generateSlug(createProductRequest.name()));
        product.setIsAvailable(true);
        product.setIsDelete(false);

        product= productRepository.save(product);


        return productMapper.mapProductToProductResponse(product);

    }
}
