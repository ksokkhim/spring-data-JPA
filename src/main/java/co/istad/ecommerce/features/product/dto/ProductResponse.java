package co.istad.ecommerce.features.product.dto;

import co.istad.ecommerce.features.category.dto.CategorySnippetResponse;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(

        String code,
        String slug,
        String name,
        String description,
        String thumbnail,
        BigDecimal unitPrice,
        Integer qty,
        Boolean isAvailable,
        Boolean isDelete,
        CategorySnippetResponse category
) {
}
