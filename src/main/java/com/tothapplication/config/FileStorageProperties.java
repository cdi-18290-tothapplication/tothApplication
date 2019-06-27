package com.tothapplication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file", ignoreUnknownFields = false)
public class FileStorageProperties {

    private String uploadDir;

    public String getUploadDir() {
        System.err.println("Upload Directory : " + uploadDir);
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
