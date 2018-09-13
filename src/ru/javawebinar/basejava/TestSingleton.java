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

        Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance.ordinal());




        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
            System.out.println(type.getContent());
        }
    }

    public enum Singleton {
        INSTANCE
    }
}