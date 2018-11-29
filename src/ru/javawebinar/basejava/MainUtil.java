package ru.javawebinar.basejava;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import ru.javawebinar.basejava.model.Organization;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.util.JsonSectionAdapter;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainUtil extends Thread {
    public void run() {
        System.out.println("1. Hello from a thread!");

    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new MainUtil();
        thread.start();
        //    thread.join();
        System.out.println("2. Hello from the main!");
    }

    private static Integer getInt() {
        return null;
    }
}