package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    private final HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }

    @Override
    protected void abstractUpdateResume(Resume r, Object searchKey) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected void abstractDeleteResume(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void abstractSaveResume(Resume r, Object searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume abstractGetResume(String uuid, Object searchKey) {
        return storage.get(uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] allResume = new Resume[storage.size()];
        storage.values().toArray(allResume);
        return allResume;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
