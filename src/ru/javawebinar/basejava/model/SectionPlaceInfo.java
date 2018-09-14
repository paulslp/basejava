package ru.javawebinar.basejava.model;

import java.time.LocalDate;

public class SectionPlaceInfo extends SectionValue{

    private String site;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String position;
    private String text;

    public SectionPlaceInfo(String name, String site, LocalDate dateStart, LocalDate dateEnd, String position, String text) {
        super(name);
        this.site = site;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.position = position;
        this.text = text;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return new StringBuilder(getName()).append(" (сайт ").append(getSite()).append(")\r\n").append(getDateStart()).append(" - ").append(getDateEnd()).append("      ").append(getPosition()).append("\r\n").append("                             ").append(getText()).toString();
    }

}
