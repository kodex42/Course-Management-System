package com.comp3000.project.cms.DAL.services.RegApplication;

import com.comp3000.project.cms.DAO.RegApplication;
import com.comp3000.project.cms.DAL.repository.RegApplicationRepository;
import javassist.NotFoundException;
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

    public RegApplication getById(Integer id) throws NotFoundException {
        return this.regApplicationRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Registration application with specified ID was not found"));
    }
}
