package ru.javawebinar.basejava;

public class MainUtil extends Thread{
    public void run(){
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