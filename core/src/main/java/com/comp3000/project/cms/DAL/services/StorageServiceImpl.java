package com.comp3000.project.cms.DAL.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javassist.NotFoundException;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class StorageServiceImpl implements StorageService {
        private final Path root = Paths.get("uploads");

        @Override
        public void init() {
            try {
                if (!Files.exists(root))
                    Files.createDirectory(root);
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize folder for upload!");
            }
        }

        @Override
        public void save(String prefix, MultipartFile file) {
            try {
                Path folderPath = this.root.resolve(prefix);
                if (Files.notExists(folderPath))
                    Files.createDirectory(folderPath);

                Path pathToFile = Paths.get(folderPath.toString(), file.getOriginalFilename());
                Files.copy(file.getInputStream(), pathToFile, REPLACE_EXISTING);
            } catch (Exception e) {
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            }
        }

        @Override
        public Resource loadAsResource(String prefix, String filename) throws NotFoundException {
            try {
                Path path = Paths.get(prefix, filename);
                Resource resource =  new UrlResource(this.root.resolve(path).toUri());
                return resource;
            } catch (Exception e) {
                throw new NotFoundException(e.toString());
            }
        }
}
