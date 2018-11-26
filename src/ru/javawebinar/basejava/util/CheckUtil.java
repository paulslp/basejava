package ru.javawebinar.basejava.util;

import java.time.LocalDate;

public class CheckUtil {

    public static String getDateChecking(LocalDate date) {
        if (date==null) {
            return "";
        }
        if (date.getYear()==3000) {
            return "наст. время";
        }
        return date.toString();

    }


    public static String getStringChecking(String data) {
        if (data==null) {
            return "";
        } else {
            return data;
        }

    }
}
