package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;


public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    protected static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullname).thenComparing(Resume::getUuid);


    @Override
    public void save(Resume r) {
        LOG.info("Update" + r);
        SK searchKey = getNotExistingSearchKey(r.getUuid());
        doSave(r, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get" + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        return doGet(uuid, searchKey);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete" + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);

    }

    @Override
    public void update(Resume r) {
        LOG.info("Update" + r);
        SK searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(r, searchKey);
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume:" + uuid + " already exist");
            throw new NotExistStorageException(uuid);
        } else {
            return searchKey;
        }

    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume:" + uuid + " not exist");
            throw new ExistStorageException(uuid);
        } else {
            return searchKey;
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("Get all sorted:");
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
