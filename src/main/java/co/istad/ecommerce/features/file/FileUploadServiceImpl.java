package co.istad.ecommerce.features.file;

import co.istad.ecommerce.features.file.dto.FileUploadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService{

    private final FileUploadRepository fileUploadRepository;
    private final FileUploadMapper fileUploadMapper;
    @Value("${file.storage-location}")
    private String storageLocation;

    @Value("${file.base-uri}")
    private String baseUri;

    public FileUploadServiceImpl(FileUploadRepository fileUploadRepository, FileUploadMapper fileUploadMapper) {
        this.fileUploadRepository = fileUploadRepository;
        this.fileUploadMapper = fileUploadMapper;
    }

    @Override
    public FileUploadResponse findByName(String name) {
        return fileUploadRepository.findByName(name)
                .map(fileUploadMapper::mapFileUploadToFileUploadResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"file not found from your part"));
    }

    @Override
    public FileUploadResponse upload(MultipartFile file) {

        // Prepare file information
        // File name
        String name = UUID.randomUUID().toString();

        // mypro.file.png
        String ext = file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        name += "." + ext; // new-unique-filename.ext

        // Create absolute path to store file
        Path path = Paths.get(storageLocation + name);

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File has been failed to upload");
        }

        return FileUploadResponse.builder()
                .name(name)
                .size(file.getSize())
                .mediaType(file.getContentType())
                .uri(baseUri + name)
                .build();
    }

    //Handle Multiple files upload
    @Override
    public List<FileUploadResponse> upload(List<MultipartFile> files) {

        List<FileUploadResponse> responses = new ArrayList<>();

        for (MultipartFile file : files) {

            String name = UUID.randomUUID().toString();

            String originalFilename = file.getOriginalFilename(); String ext = originalFilename
                    .substring(originalFilename.lastIndexOf(".") + 1);

            name += "." + ext;

            Path path = Paths.get(storageLocation + name);

            try {
                Files.copy(file.getInputStream(), path);
            } catch (IOException e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "File has been failed to upload"
                );
            }

            responses.add(
                    FileUploadResponse.builder()
                            .name(name)
                            .size(file.getSize())
                            .mediaType(file.getContentType())
                            .uri(baseUri + name)
                            .build()
            );
        }

        return responses;
    }

    //Handle Delete files upload
    @Override
    public void deleteByName(String name) {
        Path path = Paths.get(storageLocation + name);
        try {
            if (!Files.exists(path)) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "File not found"
                );
            }
            Files.delete(path);

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to delete file"
            );
        }
    }


}
