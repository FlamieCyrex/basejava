package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {


    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("The storage has been cleared");
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
            System.out.println("The resume has been updated");
        } else {
            System.out.println("There is no such resume in storage for updating (uuid: " + r.getUuid() + ")");
        }
    }

    public void save(Resume r) {
        if (size >= STORAGE_LIMIT) {
            System.out.println("There is no free space in storage for saving resume (uuid: " + r.getUuid() + ")");
        } else if (getIndex(r.getUuid()) >= 0) {
            System.out.println("This resume already exists in the storage (uuid: " + r.getUuid() + ")");
        } else {
            storage[size] = r;
            size++;
            System.out.println("The resume has been saved");
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

    public int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }


}
