package ru.javawebinar.basejava.model;

public class RecordContact {
    private String type;
    private String value;

    public RecordContact(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new StringBuilder(getType()).append(":").append(getValue()).toString();
    }


}
