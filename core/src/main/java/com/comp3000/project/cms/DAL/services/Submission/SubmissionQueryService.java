package com.comp3000.project.cms.DAL.services.Submission;

import com.comp3000.project.cms.DAL.repository.SubmissionRepository;
import com.comp3000.project.cms.DAO.Submission;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionQueryService {
    @Autowired
    private SubmissionRepository submissionRepository;

    public Submission getById(Integer id) throws NotFoundException {
        return this.submissionRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Submission with specified ID was not found"));
    }
    public List<Submission> getAllByDeliverableId(Integer id) {
        return this.submissionRepository.findByDeliverableId(id);
    }
    public Submission getByDeliverableIdAndStudentId(Integer delivId, Integer studId) {
        return this.submissionRepository.findByDeliverableIdAndStudentId(delivId, studId);
    }
}
