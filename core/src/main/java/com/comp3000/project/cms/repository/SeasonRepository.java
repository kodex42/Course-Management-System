package com.comp3000.project.cms.repository;

import com.comp3000.project.cms.DAC.Season;
import org.springframework.data.repository.CrudRepository;

public interface SeasonRepository extends CrudRepository<Season, Integer> {
    Season findByCode(String code);
}
