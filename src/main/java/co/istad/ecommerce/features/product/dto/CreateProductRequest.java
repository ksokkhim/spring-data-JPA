package co.istad.ecommerce.features.product.dto;

import co.istad.ecommerce.features.category.dto.CategorySnippetResponse;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductRequest(

        @NotBlank(message = "name is required")
        @Size(max= 255)
        String name,

        @Size(max = 500)
        String description,

        @Size(max = 255)
        String thumbnail,

        @NotNull(message = "unit price is required")
        @Min(0)
        BigDecimal unitPrice,

        @NotNull(message = "qty is required")
        @Min(0)
        Integer qty,

        @NotNull(message = "category id is required")
        @Positive
        Integer categoryId
) {
}
