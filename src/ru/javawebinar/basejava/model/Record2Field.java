package ru.javawebinar.basejava.model;

public class Record2Field extends Record1Field {


    private String field2;

    public Record2Field(String field1, String field2) {
        super(field1);
        this.field2 = field2;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    @Override
    public String toString() {
        return new StringBuilder(getField1()).append(":").append(field2).toString();
    }


}
