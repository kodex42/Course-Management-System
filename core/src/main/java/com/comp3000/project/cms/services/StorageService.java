package com.comp3000.project.cms.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {
    void init();

    void save(MultipartFile file);

    Path load(String filename);
}