package co.istad.ecommerce.features.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
        @NotBlank(message = "name is require")
        @Size(max= 50)
        String name,
        @Size(max = 50)
        String description,
        @Size(max = 50)
        String icon,
//        Boolean isDeleted,
        @Positive
        Integer parentCategoryId
) {
}
