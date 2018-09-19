package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization{
    private String name;
    private Site site;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String position;
    private String text;


    public Organization(String name, String siteName, String url, LocalDate dateStart, LocalDate dateEnd, String position, String text) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(dateStart, "dateStart must not be null");
        Objects.requireNonNull(dateEnd, "dateEnd must not be null");
        Objects.requireNonNull(position, "position must not be null");

        this.name = name;
        this.site = new Site(siteName, url);
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.position = position;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!name.equals(that.name)) return false;
        if (!site.equals(that.site)) return false;
        if (!dateStart.equals(that.dateStart)) return false;
        if (!dateEnd.equals(that.dateEnd)) return false;
        if (!position.equals(that.position)) return false;
        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + site.hashCode();
        result = 31 * result + dateStart.hashCode();
        result = 31 * result + dateEnd.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder(name).append(" (сайт ").append(site.toString()).append(")\r\n").append(dateStart).append(" - ").append(dateEnd).append("      ").append(position).append("\r\n").append("                             ").append(text).append("\r\n").toString();
    }

}
