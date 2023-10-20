package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    int count;

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }
    public void save(Resume r) {
        storage[count] = r;
        count++;
    }

    public Resume get(String uuid) {
        for (int i = 0; i < count; i++) {
            if (storage[i].toString().equals(uuid)) return storage[i];
        }
        return null;
    }

    public void delete(String uuid) {
        boolean isResumeExist = false;
        for (int i = 0; i < count; i++) {
            if (Objects.equals(storage[i].uuid, uuid)) {
                storage[i] = storage[count - 1];
                storage[count - 1] = null;
                count--;
                isResumeExist = true;
            }
        }
        if (!isResumeExist) System.out.println("There is no such resume in storage");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    public int size() {
        return count;
    }
}
