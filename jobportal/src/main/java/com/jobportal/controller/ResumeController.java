package com.jobportal.controller;

import com.jobportal.models.Resume;
import com.jobportal.service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {
    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("storageType") String storageType) {

        try {
            Resume savedResume = resumeService.uploadResume(file, storageType);
            return ResponseEntity.ok("Resume uploaded successfully! ID: " + savedResume.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading resume: " + e.getMessage());
        }
    }
}

