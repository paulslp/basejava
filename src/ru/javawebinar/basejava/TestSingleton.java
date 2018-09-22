package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.SectionType;

public class TestSingleton {
    private static TestSingleton instance;

    private TestSingleton() {
    }

    public static TestSingleton getInstance() {
        if (instance == null) {
            instance = new TestSingleton();
        }
        return instance;
    }

    public static void main(String[] args) {

        TestSingleton.getInstance().toString();
        Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance.ordinal());

        for (Singleton type : Singleton.values()) {
            System.out.println(type);
        }

        Singleton.valueOf("INSTANCE");

        SectionType type1;


        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }


    }

    public enum Singleton {
        INSTANCE, INSTANCE2
    }
}