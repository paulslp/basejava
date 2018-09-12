package ru.javawebinar.basejava.model;

public enum SectionType {
    CONTACTS("Контакты", null),
    PERSONAL("Личные качества", null),
    OBJECTIVE("Позиция", null),
    ACHIEVEMENT("Достижения", null),
    QUALIFICATIONS("Квалификация", null),
    EXPERIENCE("Опыт работы", null),
    EDUCATION("Образование", null);

    private String title;
    private String content;


    SectionType(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}