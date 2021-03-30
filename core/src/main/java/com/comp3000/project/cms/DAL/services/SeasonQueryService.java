package com.comp3000.project.cms.DAL.services;

import com.comp3000.project.cms.DAO.Season;
import com.comp3000.project.cms.DAL.repository.SeasonRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeasonQueryService {
    @Autowired
    private SeasonRepository seasonRepository;

    public Iterable<Season> getAll() {
        return this.seasonRepository.findAll();
    }

    public Season getById(Integer id) throws NotFoundException {
        return this.seasonRepository.findById(id).orElseThrow(() -> new NotFoundException("Season with specified ID was not found"));
    }
}
