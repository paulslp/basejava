package ru.javawebinar.basejava.model;

public class Record4Field extends Record3Field {

    private String field4;

    public Record4Field(String field1, String field2, String field3, String field4) {
        super(field1, field2, field3);
        this.field4 = field4;
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    @Override
    public String toString() {
        return new StringBuilder(getField1()).append("\r\n").append(getField2()).append(" ").append(getField3()).append("\r\n").append("               ").append(getField4()).toString();
    }

}
