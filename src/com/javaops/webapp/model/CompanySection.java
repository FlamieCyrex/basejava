package com.javaops.webapp.model;

import java.util.List;

public class CompanySection extends Section {
    private List<Company> companies;

    public CompanySection(List<Company> companies) {
        if (companies == null) {
            throw new IllegalArgumentException();
        }
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanySection)) return false;

        CompanySection that = (CompanySection) o;

        return companies.equals(that.companies);
    }

    @Override
    public int hashCode() {
        return companies.hashCode();
    }

    @Override
    public String toString() {
        return companies.toString();
    }
}
