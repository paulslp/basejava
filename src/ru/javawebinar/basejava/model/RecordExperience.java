package ru.javawebinar.basejava.model;

public class RecordExperience extends RecordEducation {

    private String data;

    public RecordExperience(String place, String period, String titul, String data) {
        super(place, period, titul);
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new StringBuilder(getPlace()).append("\r\n").append(getPeriod()).append(" ").append(getTitul()).append("\r\n").append("               ").append(getData()).toString();
    }

}
