package ru.javawebinar.basejava.model;

import java.util.List;

public class ListSection extends Section {
    private List<String> listItems;

    public ListSection(List<String> listItems) {
        this.listItems = listItems;
    }


    public void appendItem(String item) {
        listItems.add(item);
    }

    @Override
    public void append(String value) {

    }

    @Override
    public Object getSection() {
        return listItems;
    }



    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Object item : listItems) {
            result.append(item.toString()).append("\r\n");
        }
        return result.toString();
    }
}
