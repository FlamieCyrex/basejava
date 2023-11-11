package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];


    @Override
    protected void addResume(Resume r, int index) {
        insertResume(r, index);
        size++;
    }


    @Override
    protected Resume returnResume(String uuid, int index) {
        return storage[index];
    }


    @Override
    protected void abstractDeleteResume(int index) {
        deleteResume(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void updateResume(Resume r, int index) {
        storage[index] = r;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("The storage has been cleared");
    }


    protected abstract void deleteResume(int index);

    protected abstract void insertResume(Resume r, int index);

}
