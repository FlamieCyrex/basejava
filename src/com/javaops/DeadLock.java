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
            synchronized (object1) {
                System.out.println(Thread.currentThread() + "is locking object1");
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (object2) {
                    System.out.println(Thread.currentThread() + "is locking object1 and object2");
                }
            }
        }
    }

    public static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (object2) {
                System.out.println(Thread.currentThread() + "is locking object2");
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (object1) {
                    System.out.println(Thread.currentThread() + "is locking object1 and object2");
                }
            }
        }
    }

}
