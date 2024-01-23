package com.javaops;


public class DeadLock {
    public final static Object object1 = new Object();
    public final static Object object2 = new Object();

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        thread2.start();
    }


    public static class Thread1 extends Thread {
        @Override
        public void run() {
            deadlock(object1, object2);
        }
    }


    public static class Thread2 extends Thread {
        @Override
        public void run() {
            deadlock(object2, object1);
        }
    }

    public static void deadlock(Object obj1, Object obj2) {
        synchronized (obj1) {
            System.out.println(Thread.currentThread() + "is locking on object");
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (obj2) {
                System.out.println(Thread.currentThread() + "is locking object1 and object2");
            }
        }
    }


}
