package ru.javawebinar.basejava;

public class DeadLockExample {


    static void method(Object objectA, Object objectB) {
        synchronized (objectA) {
            System.out.println(Thread.currentThread().getName() + ":locked ObjectA");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (objectB) {
                System.out.println(Thread.currentThread().getName() + ":locked ObjectB");
            }
        }
    }

    public static void main(String[] args) {

        final Object objectA = new Object();
        final Object objectB = new Object();

        Thread threadA = new Thread(() -> method(objectA, objectB));
        Thread threadB = new Thread(() -> method(objectB, objectA));

        threadA.start();
        threadB.start();

    }
}