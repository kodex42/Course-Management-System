package com.comp3000.project.cms.web.forms;

import java.util.Map;

public class FilterForm extends Form {
    private String username;
    private Map<String, String> filters;
    private boolean hasHighlighting;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public boolean hasFilters() {
        return filters != null;
    }
}
