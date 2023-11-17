package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;


public abstract class AbstractStorage implements Storage {


    @Override
    public void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid());
        doSave(r, searchKey);
        System.out.println("The resume has been saved");
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(uuid, searchKey);

    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
        System.out.println("The resume has been deleted");
    }

    @Override
    public void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(r, searchKey);
        System.out.println("The resume has been updated");
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }

    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    protected abstract boolean isExist(Object searchKey);

    protected abstract void doUpdate(Resume r, Object searchKey);

    protected abstract void doDelete(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doSave(Resume r, Object searchKey);

    protected abstract Resume doGet(String uuid, Object searchKey);
}
