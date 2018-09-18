package ru.javawebinar.basejava.model;

public class TextSection extends Section{

    private String text;

    public TextSection(String text) {
        this.text = text;
    }

    public Object getSection() {
        return text;
    }


    @Override
    public void append(String value) {
        text = value.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection that = (TextSection) o;

        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }


}
