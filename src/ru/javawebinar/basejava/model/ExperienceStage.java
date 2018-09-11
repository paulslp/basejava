package ru.javawebinar.basejava.model;

public class ExperienceStage {

    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }

    public String getPositionWork() {
        return positionWork;
    }

    public void setPositionWork(String positionWork) {
        this.positionWork = positionWork;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    private String placeOfWork;
    private String positionWork;
    private String period;
    private String skills;

}
