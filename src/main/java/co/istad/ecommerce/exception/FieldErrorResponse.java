package co.istad.ecommerce.exception;

import lombok.Builder;

@Builder
public record FieldErrorResponse<T>(
      String field,
        String message

) {
}
