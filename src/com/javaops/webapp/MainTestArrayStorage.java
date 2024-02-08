package com.javaops.webapp;

import com.javaops.webapp.model.Resume;
import com.javaops.webapp.storage.MapResumeStorage;


/**
 * Test for your com.javaops.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final MapResumeStorage ARRAY_STORAGE = new MapResumeStorage();

    public static void main(String[] args) {

        final Resume RESUME_1 = new Resume("uuid1", "fullname1");

        final Resume RESUME_2 = new Resume("uuid2", "fullname2");

        final Resume RESUME_3 = new Resume("uuid3", "fullname3");


        ARRAY_STORAGE.save(RESUME_1);
        ARRAY_STORAGE.save(RESUME_2);
        ARRAY_STORAGE.save(RESUME_3);


        System.out.println("Get r1: " + ARRAY_STORAGE.get(RESUME_1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("uuid2"));

        ARRAY_STORAGE.update(RESUME_2);

        printAll();
        ARRAY_STORAGE.delete(RESUME_1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());


    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
