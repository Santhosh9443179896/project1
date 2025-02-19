package com.jobportal.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class S3StorageService {
    private final S3Client s3Client;
    private final String bucketName = "your-bucket-name"; // Replace with your AWS S3 bucket

    public S3StorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = "resumes/" + file.getOriginalFilename();

        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build(),
                Paths.get(file.getOriginalFilename()));

        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
    }
}

