package ru.javawebinar.basejava.model;

public class Record3Field extends Record2Field {
    private String field3;

    public Record3Field(String field1, String field2, String field3) {
        super(field1, field2);
        this.field3 = field3;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    @Override
    public String toString() {
        return new StringBuilder(getField1()).append("\r\n").append(getField2()).append("       ").append(getField3()).toString();
    }

}
