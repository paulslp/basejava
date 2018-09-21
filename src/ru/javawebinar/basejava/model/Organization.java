package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * gkislin
 * 19.07.2016
 */
public class Organization {
    private final Link homePage;

    private List<PersonalData> personalDataList;

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");

        this.homePage = new Link(name, url);
        personalDataList = new ArrayList<>();
        addPersonalData(startDate,endDate,title,description);
    }

    public void addPersonalData(LocalDate startDate, LocalDate endDate, String title, String description){
        personalDataList.add(new PersonalData(startDate,endDate,title,description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return personalDataList.equals(that.personalDataList);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + personalDataList.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String resultPersonalData="";

        for (PersonalData persData: personalDataList){
            resultPersonalData = resultPersonalData +persData.toString()+ ";";
        }
        return "Organization{" +
                "homePage=" + homePage + "," +
                 resultPersonalData +
                '}';
    }
}