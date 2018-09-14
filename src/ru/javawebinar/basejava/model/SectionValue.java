package ru.javawebinar.basejava.model;

public class SectionValue {
    private String name;

    public SectionValue(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return new StringBuilder(getName()).append("\r\n").toString();
    }
}
