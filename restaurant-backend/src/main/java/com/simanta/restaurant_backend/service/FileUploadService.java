package com.simanta.restaurant_backend.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID; 
 
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.simanta.restaurant_backend.exception.FileUploadService_Exception;
 
@Service 
public class FileUploadService {
 
    public String uploadFile(MultipartFile file,String folderName) {

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path uploadPath = Paths.get("uploads/" + folderName);

            Files.createDirectories(uploadPath);

            Files.copy(file.getInputStream(),uploadPath.resolve(fileName),StandardCopyOption.REPLACE_EXISTING);

            return "/images/" + folderName + "/" + fileName;

        } catch (IOException e) {

            throw new FileUploadService_Exception("File upload failed.");
        }
    }

}
