package ru.javawebinar.basejava.model;

public enum SectionType {
    PERSONAL("Личные качества") {
        @Override
        public String toHtml0(Section value) {
            return getTitle() + ": " + ((TextSection) value).getContent();
        }
    },
    OBJECTIVE("Позиция") {
        @Override
        public String toHtml0(Section value) {
            return getTitle() + ": " + ((TextSection) value).getContent();
        }
    },
    ACHIEVEMENT("Достижения") {
        @Override
        public String toHtml0(Section value) {
            return getTitle() + ": " + String.join(",", ((ListSection) value).getItems());
        }

        @Override
        public Section htmlToSection(String value) {
            return new ListSection(value.split(","));
        }
    },
    QUALIFICATIONS("Квалификация") {
        @Override
        public String toHtml0(Section value) {
            return getTitle() + ": " + String.join(",", ((ListSection) value).getItems());
        }

        @Override
        public Section htmlToSection(String value) {
            return new ListSection(value.split(","));
        }


    },
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(Section value) {
        return value.toString();
    }

    public String toHtml(Section value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public Section htmlToSection(String value) {
        return new TextSection(value);
    }


}