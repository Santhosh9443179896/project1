package com.jobportal.service;

import com.jobportal.models.Resume;
import com.jobportal.repository.ResumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final S3StorageService s3StorageService;

    public ResumeService(ResumeRepository resumeRepository, S3StorageService s3StorageService) {
        this.resumeRepository = resumeRepository;
        this.s3StorageService = s3StorageService;
    }

    public Resume uploadResume(MultipartFile file, String storageType) throws IOException {
        Resume resume = new Resume();
        resume.setFileName(file.getOriginalFilename());
        resume.setFileType(file.getContentType());
        resume.setStorageType(storageType);

        if ("DATABASE".equalsIgnoreCase(storageType)) {
            resume.setFileData(file.getBytes());  // Store in MySQL
        } else if ("CLOUD".equalsIgnoreCase(storageType)) {
            String fileUrl = s3StorageService.uploadFile(file);  // Upload to AWS S3
            resume.setFileUrl(fileUrl);  // Save URL in MySQL
        } else {
            throw new IllegalArgumentException("Invalid storage type. Use DATABASE or CLOUD.");
        }

        return resumeRepository.save(resume);
    }
}

