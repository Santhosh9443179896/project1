package com.jobportal.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;

    @Lob  // Stores large binary files
    private byte[] fileData;

    private String fileUrl;  // Stores URL if uploaded to cloud

    private String storageType; // DATABASE or CLOUD
}
