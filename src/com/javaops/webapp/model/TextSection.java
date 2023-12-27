package com.javaops.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Objects;
@XmlAccessorType(XmlAccessType.FIELD)
public class TextSection extends Section {
    private String title;

    public TextSection(String title) {
        Objects.requireNonNull(title, "title can`t be null");
        this.title = title;
    }

    public TextSection() {
    }

    public String getTitle() {

        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextSection)) return false;

        TextSection that = (TextSection) o;

        return title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public String toString() {
        return title;
    }
}
