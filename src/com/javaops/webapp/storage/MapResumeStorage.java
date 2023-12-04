package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final HashMap<String, Resume> map = new HashMap<>();

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doUpdate(Resume r, Resume searchKey) {
        map.put(searchKey.getUuid(), r);
    }

    @Override
    protected void doDelete(Resume searchKey) {
        map.remove(searchKey.getUuid());
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void doSave(Resume r, Resume searchKey) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(String uuid, Resume searchKey) {
        return map.get(uuid);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected List<Resume> doGetAll() {
        Resume[] allResume = new Resume[map.size()];
        map.values().toArray(allResume);
        return Arrays.asList(allResume);
    }

    @Override
    public int size() {
        return map.size();
    }
}
