package com.comp3000.project.cms.DAL.services.RegApplication;

import com.comp3000.project.cms.DAO.RegApplication;
import com.comp3000.project.cms.DAL.repository.RegApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegApplicationCommandService {
    @Autowired
    private RegApplicationRepository regApplicationRepository;

    public RegApplication create(RegApplication app) {
        return this.regApplicationRepository.save(app);
    }

    public void deleteById(Integer id) {
        this.regApplicationRepository.deleteById(id);
    }

    public void delete(RegApplication app) {
        this.deleteById(app.getId());
    }
}
