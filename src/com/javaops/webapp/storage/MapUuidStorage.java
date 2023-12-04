package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MapUuidStorage extends AbstractStorage<String> {
    private final HashMap<String, Resume> map = new HashMap<>();

    @Override
    protected boolean isExist(String searchKey) {
        return map.containsKey(searchKey);
    }

    @Override
    protected void doUpdate(Resume r, String searchKey) {
        map.put(searchKey, r);
    }

    @Override
    protected void doDelete(String searchKey) {
        map.remove(searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doSave(Resume r, String searchKey) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(String uuid, String searchKey) {
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
