package co.istad.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CategoryResponse(
        Integer id,

        String name,

        String description,
        String icon,
        Boolean isDeleted,
        CategoryResponse parentCategory
) {
}
