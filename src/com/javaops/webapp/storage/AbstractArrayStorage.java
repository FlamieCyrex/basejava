package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("There is no such resume in storage (uuid: " + uuid + ")");
            return null;
        }
    }

    protected abstract int getIndex(String uuid);

}
