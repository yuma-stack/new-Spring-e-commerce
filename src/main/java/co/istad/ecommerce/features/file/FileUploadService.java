package co.istad.ecommerce.features.file;

import co.istad.ecommerce.features.file.dto.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface FileUploadService {

    FileUploadResponse findByName(String name);

    FileUploadResponse upload(MultipartFile file);

    /**
     * Upload multiple files
     * @param files is a list for multiple upload
     * @return list of {@link FileUploadResponse }
     */
    List<FileUploadResponse> upload(List<MultipartFile> files);

    /**
     *
     * @param name we deleted using file name
     */
    void deleteByName(String name);
}
