package ru.javawebinar.basejava.model;

public class RecordEducation {
    private String place;
    private String period;
    private String titul;

    public RecordEducation(String place, String period, String titul) {
        this.place = place;
        this.period = period;
        this.titul = titul;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTitul() {
        return titul;
    }

    public void setTitul(String titul) {
        this.titul = titul;
    }

    @Override
    public String toString() {
        return new StringBuilder(getPlace()).append("\r\n").append(getPeriod()).append("       ").append(getTitul()).toString();
    }


}
