package com.javaops.webapp.model;

import java.util.List;

public class ListSection extends Section {
    private List<String> elements;

    public ListSection(List<String> elements) {
        if (elements == null) {
            throw new IllegalArgumentException("elements can`t be null");
        }
        this.elements = elements;
    }

    public List<String> getElements() {
        return elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListSection)) return false;

        ListSection that = (ListSection) o;

        return elements.equals(that.elements);
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }

    @Override
    public String toString() {
        return elements.toString();
    }
}
