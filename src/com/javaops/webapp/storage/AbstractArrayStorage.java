package com.javaops.webapp.storage;

import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected static final int STORAGE_LIMIT = 10000;
    protected int size;

    @Override
    protected void doSave(Resume r, Integer searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            insertResume(r, searchKey);
            size++;
        }
    }


    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }


    @Override
    protected void doDelete(Integer searchKey) {
        deleteResume(searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void doUpdate(Resume r, Integer searchKey) {
        storage[searchKey] = r;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("The storage has been cleared");
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    protected abstract void deleteResume(int index);

    protected abstract void insertResume(Resume r, int index);

}
