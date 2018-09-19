package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListSection implements Section {
    private List<String> listItems;

    public ListSection(List<String> listItems) {
        Objects.requireNonNull(listItems, "listItems must not be null");
        this.listItems = listItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return listItems.equals(that.listItems);
    }

    @Override
    public int hashCode() {
        return listItems.hashCode();
    }

    @Override
    public String toString() {
        return listItems.toString();
    }
}
