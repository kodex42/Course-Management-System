package com.comp3000.project.cms.converters;

import com.comp3000.project.cms.SDC.*;
import com.comp3000.project.cms.components.CMS;
import com.comp3000.project.cms.forms.FilterForm;
import com.comp3000.project.cms.services.User.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FormFilterConverter extends Converter<CourseOfferingFilterStrategy, FilterForm> {
    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private CMS cms;

    @Override
    public CourseOfferingFilterStrategy convert(FilterForm filterForm) {
        List<CourseOfferingFilterStrategy> strategies = new ArrayList<>();
        Map<String, String> filterSelections = filterForm.getFilters();

        if (filterSelections.containsKey("Code") && !filterSelections.get("Code").equals(""))
            strategies.add(new CourseOfferingCodeFilter(filterSelections.get("Code")));

        if (filterSelections.containsKey("Term") && !filterSelections.get("Term").equals(""))
            strategies.add(new CourseOfferingTermFilter(filterSelections.get("Term")));

        if (filterForm.isHasHighlighting())
            return new CourseOfferingAvailabilityHighlighting(new CompositeCourseOfferingFilter(strategies), cms, userQueryService.getByUsername(filterForm.getUsername()));
        return new CompositeCourseOfferingFilter(strategies);
    }
}
