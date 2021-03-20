package com.comp3000.project.cms.services.RegApplication;

import com.comp3000.project.cms.DAC.RegApplication;
import com.comp3000.project.cms.repository.RegApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegApplicationQueryService {
    @Autowired
    private RegApplicationRepository regApplicationRepository;

    public RegApplication getByEmail(String email) {
        return this.regApplicationRepository.findByEmail(email);
    }

    public Iterable<RegApplication> getAll() {
        return this.regApplicationRepository.findAll();
    }

    public Optional<RegApplication> getById(Integer id) {
        return this.regApplicationRepository.findById(id);
    }
}
