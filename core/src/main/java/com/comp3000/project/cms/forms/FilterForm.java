package com.comp3000.project.cms.forms;

import java.util.Map;

public class FilterForm extends Form {
    private Map<String, String> filters;
    private boolean hasHighlighting;

    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    public boolean isHasHighlighting() {
        return hasHighlighting;
    }

    public void setHasHighlighting(boolean hasHighlighting) {
        this.hasHighlighting = hasHighlighting;
    }
}
