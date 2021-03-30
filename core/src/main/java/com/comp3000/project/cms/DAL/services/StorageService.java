package com.comp3000.project.cms.DAL.services;

import javassist.NotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface StorageService {
    void init();
    void save(String prefix, MultipartFile file, String filenameOverride);
    void save(String prefix, MultipartFile file);
    Resource loadAsResource(String prefix, String filename) throws NotFoundException;
}