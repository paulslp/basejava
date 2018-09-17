package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE("Тел."),
    EMAIL("e-mail"),
    SKYPE("skype");


    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}