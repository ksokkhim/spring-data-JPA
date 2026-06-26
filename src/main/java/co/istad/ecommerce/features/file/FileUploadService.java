package co.istad.ecommerce.features.file;

import co.istad.ecommerce.features.file.dto.FileUploadResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {

    FileUploadResponse findByName(String name);

    Page<FileUploadResponse> findAll(int pageNumber, int pageSize);
    FileUploadResponse upload(MultipartFile file);

    List<FileUploadResponse> uploadMultiple(List<MultipartFile> files);



    void delete(String name);
}
