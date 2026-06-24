package co.istad.ecommerce.features.file.dto;

import lombok.Builder;

@Builder
public record FileUploadResponse(
        String name,
        String caption,
        Long size,
        String mediaType,
//        http://localhost:9090/file/dog-working.jpg
        String uri
) {
}
