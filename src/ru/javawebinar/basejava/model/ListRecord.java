package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListRecord<T> {
    private List<T> listRecord = new ArrayList<>();


    public void add(T record) {
        listRecord.add(record);
    }

    public String getAll() {
        StringBuilder result = new StringBuilder();
        for (T record : listRecord) {
            result.append(record.toString()).append("\r\n");
        }
        return result.toString();
    }
}
