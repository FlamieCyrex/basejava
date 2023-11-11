package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected void addResume(Resume r, int index) {
        storage.add(r);
    }

    @Override
    protected Resume returnResume(String uuid, int index) {
        return storage.get(getIndex(uuid));
    }

    @Override
    protected void abstractDeleteResume(int index) {
        storage.remove(index);
    }

    @Override
    protected void updateResume(Resume r, int index) {
        storage.set(index, r);
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

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return storage.indexOf(searchKey);
    }
}
