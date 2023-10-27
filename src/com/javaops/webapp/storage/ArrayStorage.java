package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {


    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void deleteResume(int index) {
        storage[index].uuid = storage[size - 1].uuid;
        storage[size - 1] = null;
    }

    @Override
    protected void insertResume(Resume r, int index) {
        storage[size] = r;
    }
}
