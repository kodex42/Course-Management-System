package com.comp3000.project.cms.DAL.services;

import com.comp3000.project.cms.DAO.Season;
import com.comp3000.project.cms.DAL.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeasonQueryService {
    @Autowired
    private SeasonRepository seasonRepository;

    public Iterable<Season> getAll() {
        return this.seasonRepository.findAll();
    }

    public Optional<Season> getById(Integer id) {
        return this.seasonRepository.findById(id);
    }
}
