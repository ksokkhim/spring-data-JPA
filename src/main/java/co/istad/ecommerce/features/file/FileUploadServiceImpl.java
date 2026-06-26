package co.istad.ecommerce.features.file;


import co.istad.ecommerce.features.file.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService{

    private final FileUploadRepository fileUploadRepository;
    private final FileUploadMapper fileUploadMapper;

    @Value("${file.storage-location}")
    private String storageLocation;




    @Override
    public List<FileUploadResponse> uploadMultiple(List<MultipartFile> files) {
        // YOUR MORE LOGIC HERE...
        return files.stream()
                .map(this::saveFile)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String name) {
        FileUpload fileUpload = fileUploadRepository.findByName(name)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @Override
    public FileUploadResponse findByName(String name) {
        return fileUploadRepository.findByName(name)
                .map(fileUploadMapper::mapFileUploadToFileUploadResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File has not been found"));
    }

    // find all fileupload
    @Override
    public Page<FileUploadResponse> findAll(int pageNumber, int pageSize) {

        Sort sortBYId = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortBYId);


        Page<FileUpload> fileUploadResponses = fileUploadRepository.findAll(pageRequest);

        return fileUploadResponses.map(fileUploadMapper::mapFileUploadToFileUploadResponse);
    }

    @Override
    public FileUploadResponse upload(MultipartFile file) {
        return saveFile(file);
    }


    private FileUploadResponse saveFile(MultipartFile file) {
        // Prepare file information
        // File name
        String name = UUID.randomUUID().toString();

        // mypro.file.png
        String ext = file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        // Create absolute path to store file
        Path path = Paths.get(storageLocation + name + "." + ext);

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File has been failed to upload");
        }

        // Save information file into db
        FileUpload fileUpload = new FileUpload();
        fileUpload.setName(name);
        fileUpload.setExtension(ext);
        fileUpload.setCaption("ISTAD - Advanced IT Institute in Cambodia");
        fileUpload.setSize(file.getSize());
        fileUpload.setMediaType(file.getContentType());
        fileUploadRepository.save(fileUpload);

        return fileUploadMapper.mapFileUploadToFileUploadResponse(fileUpload);
    }




//    @Override
//    public void delete(String name) {
//        Path folder = Paths.get(storageLocation);
//        try (var stream = Files.list(folder)) {
//            Path targetPath = stream
//                    .filter(path -> !Files.isDirectory(path))
//                    .filter(path -> {
//                        String filename = path.getFileName().toString();
//                        return filename.startsWith(name + ".");
//                    })
//                    .findFirst()
//                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + name));
//            String filenameStr = targetPath.getFileName().toString();
//            String ext = filenameStr.substring(filenameStr.lastIndexOf("."));
//            Files.delete(targetPath);
//        } catch (IOException e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete file: " + name, e);
//        }
//    }


}
