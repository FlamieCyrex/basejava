package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {


    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
    }

    @Override
    protected void insertResume(Resume r, int index) {
        int nestedIndex = -index - 1;
        System.arraycopy(storage, nestedIndex, storage, nestedIndex + 1, size - nestedIndex);
        storage[nestedIndex] = r;
    }

}
