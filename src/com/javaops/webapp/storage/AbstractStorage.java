package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;


public abstract class AbstractStorage implements Storage {


    @Override
    public void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid());
        abstractSaveResume(r, searchKey);
        System.out.println("The resume has been saved");
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return abstractGetResume(uuid, searchKey);

    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        abstractDeleteResume(searchKey);
        System.out.println("The resume has been deleted");
    }

    @Override
    public void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        abstractUpdateResume(r, searchKey);
        System.out.println("The resume has been updated");
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getIndex(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }

    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getIndex(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    protected abstract boolean isExist(Object searchKey);

    protected abstract void abstractUpdateResume(Resume r, Object searchKey);

    protected abstract void abstractDeleteResume(Object searchkey);

    protected abstract int getIndex(String uuid);

    protected abstract void abstractSaveResume(Resume r, Object searchKey);

    protected abstract Resume abstractGetResume(String uuid, Object searchKey);
}
