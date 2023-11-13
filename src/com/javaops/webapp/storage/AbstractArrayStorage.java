package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected static final int STORAGE_LIMIT = 10000;
    protected int size;

    @Override
    protected void abstractSaveResume(Resume r, Object searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            insertResume(r, (int) searchKey);
            size++;
        }
    }


    @Override
    protected Resume abstractGetResume(String uuid, Object searchKey) {
        return storage[(Integer) searchKey];
    }


    @Override
    protected void abstractDeleteResume(Object searchKey) {
        deleteResume((int) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void abstractUpdateResume(Resume r, Object searchKey) {
        storage[(Integer) searchKey] = r;
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

    @Override
    protected boolean isExist(Object searchKey) {
        if ((Integer) searchKey >= 0) {
            return true;
        } else {
            return false;
        }
    }

    protected abstract void deleteResume(int index);

    protected abstract void insertResume(Resume r, int index);

}
