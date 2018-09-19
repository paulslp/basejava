package ru.javawebinar.basejava.model;

import java.util.Objects;

public class Site {
    private String name;
    private String url;

    public Site(String name, String url) {
        Objects.requireNonNull(name, "Site name must not be null");
        this.name = name;
        this.url = url;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Site site = (Site) o;

        if (!name.equals(site.name)) return false;
        return url != null ? url.equals(site.url) : site.url == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name + " " + url;
    }
}
