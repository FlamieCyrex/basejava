package com.javaops.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

import static com.javaops.webapp.util.DateUtil.of;

public class Period implements Serializable {
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String description;


    public Period(int startYear, Month startMonth, String title, String description) {
        Objects.requireNonNull(startMonth, "startMonth can`t be null");
        Objects.requireNonNull(title, "title can`t be null");
        this.startDate = of(startYear, startMonth);
        this.endDate = LocalDate.now();
        this.title = title;
        this.description = description;
    }

    public Period(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
        Objects.requireNonNull(startMonth, "startMonth can`t be null");
        Objects.requireNonNull(endMonth, "endMonth can`t be null");
        Objects.requireNonNull(title, "title can`t be null");

        this.startDate = of(startYear, startMonth);
        this.endDate = of(endYear, endMonth);
        this.title = title;
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Period)) return false;

        Period period = (Period) o;

        if (!startDate.equals(period.startDate)) return false;
        if (!endDate.equals(period.endDate)) return false;
        if (!title.equals(period.title)) return false;
        return Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return startDate + "-" + endDate + " " + "\n" + title + " \n" + description;
    }
}
