package com.comp3000.project.cms.DAL.repository;

import com.comp3000.project.cms.DAO.Submission;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubmissionRepository extends CrudRepository<Submission, Integer> {
    List<Submission> findByDeliverableId(Integer id);
    Submission findByDeliverableIdAndStudentId(Integer delivId, Integer studId);
}
