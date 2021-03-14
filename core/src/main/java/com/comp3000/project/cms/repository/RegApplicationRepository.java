package com.comp3000.project.cms.repository;

import com.comp3000.project.cms.DAC.RegApplication;
import org.springframework.data.repository.CrudRepository;

public interface RegApplicationRepository extends CrudRepository<RegApplication, Integer> {
    RegApplication findByEmail(String email);
}
