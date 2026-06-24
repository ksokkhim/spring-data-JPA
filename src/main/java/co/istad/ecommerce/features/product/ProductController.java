package co.istad.ecommerce.features.product;

import co.istad.ecommerce.features.product.dto.CreateProductRequest;
import co.istad.ecommerce.features.product.dto.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductResponse createNew(
            @Valid
            @RequestBody
            CreateProductRequest createProductRequest){
        return productService.createNew(createProductRequest);
    }

    @GetMapping
    public Page<ProductResponse> findAll( @RequestParam(required = false, defaultValue = "0") int pageNumber,
                                          @RequestParam(required = false, defaultValue = "25") int pageSize){
        return productService.findAll(pageNumber, pageSize);


    }
}
