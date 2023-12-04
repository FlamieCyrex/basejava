package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected void doSave(Resume r, Integer searchKey) {
        storage.add(r);
    }

    @Override
    protected Resume doGet(String uuid, Integer searchKey) {
        return storage.get(getSearchKey(uuid));
    }

    @Override
    protected void doDelete(Integer searchKey) {
        storage.remove(searchKey.intValue());
    }

    @Override
    protected void doUpdate(Resume r, Integer searchKey) {
        storage.set(searchKey, r);
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

    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }
}
