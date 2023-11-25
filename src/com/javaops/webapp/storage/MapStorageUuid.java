package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MapStorageUuid extends AbstractStorage {
    private final HashMap<String, Resume> Map = new HashMap<>();

    @Override
    protected boolean isExist(Object searchKey) {
        return Map.containsKey((String) searchKey);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        Map.put((String) searchKey, r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        Map.remove((String) searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        Map.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(String uuid, Object searchKey) {
        return Map.get(uuid);
    }

    @Override
    public void clear() {
        Map.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        Resume[] allResume = new Resume[Map.size()];
        Map.values().toArray(allResume);
        Arrays.sort(allResume,RESUME_COMPARATOR);
        return Arrays.asList(allResume);
    }

    @Override
    public int size() {
        return Map.size();
    }
}
