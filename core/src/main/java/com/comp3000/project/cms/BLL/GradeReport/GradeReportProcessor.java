package com.comp3000.project.cms.BLL.GradeReport;

import com.comp3000.project.cms.DAO.Deliverable;
import com.comp3000.project.cms.DAO.User;
import com.comp3000.project.cms.exception.CannotParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public abstract class GradeReportProcessor<T> {
    public abstract T createReport(Deliverable deliv, List<User> students) throws IOException;
    public abstract void uploadReport(Deliverable deliverable, T t) throws IOException, CannotParseException;
}
