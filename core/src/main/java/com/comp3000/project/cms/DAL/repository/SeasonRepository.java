package com.comp3000.project.cms.DAL.repository;

import com.comp3000.project.cms.DAO.Season;
import org.springframework.data.repository.CrudRepository;

public interface SeasonRepository extends CrudRepository<Season, Integer> {
    Season findByCode(String code);
}
