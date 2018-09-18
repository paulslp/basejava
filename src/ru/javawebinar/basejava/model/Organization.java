package ru.javawebinar.basejava.model;

import java.time.LocalDate;

public class Organization{
    private String name;
    private String site;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String position;
    private String text;


    public Organization(String name, String site, LocalDate dateStart, LocalDate dateEnd, String position, String text) {
        this.name = name;
        this.site = site;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.position = position;
        this.text = text;
    }


    @Override
    public String toString() {
        return new StringBuilder(name).append(" (сайт ").append(site).append(")\r\n").append(dateStart).append(" - ").append(dateEnd).append("      ").append(position).append("\r\n").append("                             ").append(text).toString();
    }

}
