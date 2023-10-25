package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("The storage has been cleared");
    }

    @Override
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
            System.out.println("The resume has been updated");
        } else {
            System.out.println("There is no such resume in storage for updating (uuid: " + r.getUuid() + ")");
        }
    }

    @Override
    public void save(Resume r) {
        int insertionPoint = getIndex(r.getUuid()) + 1;
        if (size >= STORAGE_LIMIT) {
            System.out.println("There is no free space in storage for saving resume (uuid: " + r.getUuid() + ")");
        } else if (insertionPoint > 0) {
            System.out.println("This resume already exists in the storage (uuid: " + r.getUuid() + ")");
        } else {
            System.arraycopy(storage, -insertionPoint, storage, -insertionPoint + 1, size + insertionPoint);
            storage[-insertionPoint] = r;
            size++;
            System.out.println("The resume has been saved");
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
            storage[size - 1] = null;
            size--;
            System.out.println("The resume has been deleted");
        } else
            System.out.println("There is no such resume in storage (uuid: " + uuid + ")");

    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }


    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }


}
