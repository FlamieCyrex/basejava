package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MapUuidStorage extends AbstractStorage {
    private final HashMap<String, Resume> map = new HashMap<>();

    @Override
    protected boolean isExist(Object searchKey) {
        return map.containsKey((String) searchKey);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        map.put((String) searchKey, r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        map.remove((String) searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(String uuid, Object searchKey) {
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
