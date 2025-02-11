package com.example.demo.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;
    // Phương thức upload hỗ trợ base64
    public String uploadFile(byte[] fileBytes) throws IOException {
        // Create a temporary file to upload
        File tempFile = File.createTempFile("upload", ".tmp");
        
        // Write the byte array into the temporary file
        Files.write(tempFile.toPath(), fileBytes);

        // Upload the file to Cloudinary
        Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());

        // Return the URL of the uploaded image
        return (String) uploadResult.get("url");
    }
    public String uploadFile(MultipartFile file) throws IOException {
        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return (String) uploadResult.get("url");
    }
}
