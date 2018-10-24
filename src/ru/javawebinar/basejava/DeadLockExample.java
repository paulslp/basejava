package ru.javawebinar.basejava;

public class DeadLockExample {

    public static void main(String[] args) {

        final Object objectA = new Object();
        final Object objectB = new Object();

        Object o = new Object();
        Thread threadA = new Thread() {
            public void run() {
                synchronized (objectA) {
                    System.out.println(Thread.currentThread().getName() + ":locked ObjectA");

                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (objectB) {
                        System.out.println(Thread.currentThread().getName() + ":locked ObjectB");
                    }

                }

            }
        };

        Thread threadB = new Thread() {
            public void run() {
                synchronized (objectB) {
                    System.out.println(Thread.currentThread().getName() + ":locked ObjectB");

                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (objectA) {
                        System.out.println(Thread.currentThread().getName() + ":locked ObjectA");
                    }

                }

            }
        };

        threadA.start();

        threadB.start();
        System.out.println("End");
    }
}