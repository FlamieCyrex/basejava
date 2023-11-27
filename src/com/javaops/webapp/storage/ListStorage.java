package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected void doSave(Resume r, Object searchKey) {
        storage.add(r);
    }

    @Override
    protected Resume doGet(String uuid, Object searchKey) {
        return storage.get((int) getSearchKey(uuid));
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storage.set((Integer) searchKey, r);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public List<Resume> doGetAll() {
        return storage;
    }

    @Override
    public void clear() {
        storage.clear();
        System.out.println("The storage has been cleared");
    }

    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "searchName");
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(searchKey.getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }
}
