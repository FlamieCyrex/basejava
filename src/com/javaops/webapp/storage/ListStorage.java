package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected void abstractSaveResume(Resume r, Object searchKey) {
        storage.add(r);
    }

    @Override
    protected Resume abstractGetResume(String uuid, Object searchKey) {
        return storage.get((int)getSearchKey(uuid));
    }

    @Override
    protected void abstractDeleteResume(Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected void abstractUpdateResume(Resume r, Object searchKey) {
        storage.set((Integer) searchKey, r);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public Resume[] getAll() {
        Resume[] allResume = new Resume[storage.size()];
        storage.toArray(allResume);
        return allResume;
    }

    @Override
    public void clear() {
        storage.clear();
        System.out.println("The storage has been cleared");
    }

    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        for (Resume r : storage) {
            if (r.uuid.equals(searchKey.uuid)) {
                return storage.indexOf(r);
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        if ((Integer) searchKey >= 0) {
            return true;
        } else {
            return false;
        }
    }
}
