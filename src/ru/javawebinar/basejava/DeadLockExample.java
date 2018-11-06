package ru.javawebinar.basejava;

public class DeadLockExample {


    static void method(String objectA, String objectB) {
        synchronized (objectA) {
            System.out.println(Thread.currentThread().getName() + ":locked " + objectA);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (objectB) {
                System.out.println(Thread.currentThread().getName() + ":locked " + objectA);
            }
        }
    }

    public static void main(String[] args) {

        final String objectA = "objectA";
        final String objectB = "objectB";

        Thread threadA = new Thread(() -> method(objectA, objectB));
        Thread threadB = new Thread(() -> method(objectB, objectA));

        threadA.start();
        threadB.start();

    }
}