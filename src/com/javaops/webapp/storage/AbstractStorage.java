package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;


public abstract class AbstractStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected int size;

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid()) + 1;
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else if (index > 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            addResume(r, index);
            System.out.println("The resume has been saved");
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return returnResume(uuid, index);
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            abstractDeleteResume(index);
            System.out.println("The resume has been deleted");
        }
    }

    @Override
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.uuid);
        } else {
            updateResume(r, index);
            System.out.println("The resume has been updated");
        }
    }

    protected abstract void updateResume(Resume r, int index);

    protected abstract void abstractDeleteResume(int index);

    protected abstract int getIndex(String uuid);

    protected abstract void addResume(Resume r, int index);

    protected abstract Resume returnResume(String uuid, int index);
}
