package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    protected final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];

    int size;

    public int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(storage[i].uuid, uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("The storage has been cleared");
    }

    public void update(Resume r) {
        int index = getIndex(r.uuid);
        if (index >= 0) {
            storage[index] = r;
            System.out.println("The resume has been updated");
        } else {
            System.out.println("There is no such resume in storage for updating (uuid: " + r.uuid + ")");
        }
    }

    public void save(Resume r) {
        if (size >= STORAGE_LIMIT) {
            System.out.println("There is no free space in storage for saving resume (uuid: " + r.uuid + ")");
        } else if (getIndex(r.uuid) >= 0) {
            System.out.println("This resume already exists in the storage (uuid: " + r.uuid + ")");
        } else {
            storage[size] = r;
            size++;
            System.out.println("The resume has been saved");
        }
    }


    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            return null;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index].uuid = storage[size - 1].uuid;
            storage[size - 1] = null;
            size--;
            System.out.println("The resume has been deleted");
        } else
            System.out.println("There is no such resume in storage (uuid: " + uuid + ")");
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }


}
