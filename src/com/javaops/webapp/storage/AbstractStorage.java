package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;


public abstract class AbstractStorage<SK> implements Storage {
    protected static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullname).thenComparing(Resume::getUuid);


    @Override
    public void save(Resume r) {
        SK searchKey = getNotExistingSearchKey(r.getUuid());
        doSave(r, searchKey);
        System.out.println("The resume has been saved");
    }

    @Override
    public Resume get(String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        return doGet(uuid, searchKey);

    }

    @Override
    public void delete(String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
        System.out.println("The resume has been deleted");
    }

    @Override
    public void update(Resume r) {
        SK searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(r, searchKey);
        System.out.println("The resume has been updated");
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }

    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> listResume = doGetAll();
        listResume.sort(RESUME_COMPARATOR);
        return listResume;
    }

    protected abstract boolean isExist(SK searchKey);

    protected abstract void doUpdate(Resume r, SK searchKey);

    protected abstract void doDelete(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract void doSave(Resume r, SK searchKey);

    protected abstract Resume doGet(String uuid, SK searchKey);

    protected abstract List<Resume> doGetAll();
}
