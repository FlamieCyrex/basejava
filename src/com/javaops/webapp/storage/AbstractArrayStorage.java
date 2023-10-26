package com.javaops.webapp.storage;

import com.javaops.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    public final void save(Resume r) {
        int index = getIndex(r.getUuid()) + 1;
        if (size >= STORAGE_LIMIT) {
            System.out.println("There is no free space in storage for saving resume (uuid: " + r.getUuid() + ")");
        } else if (index > 0) {
            System.out.println("This resume already exists in the storage (uuid: " + r.getUuid() + ")");
        } else {
            System.arraycopy(storage, -index, storage, -index + 1, size + index);
            storage[-index] = r;
            size++;
            System.out.println("The resume has been saved");
        }
    }

    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("There is no such resume in storage (uuid: " + uuid + ")");
            return null;
        }
    }

    @Override
    public final void delete(String uuid) {
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
    public final void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
            System.out.println("The resume has been updated");
        } else {
            System.out.println("There is no such resume in storage for updating (uuid: " + r.getUuid() + ")");
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("The storage has been cleared");
    }


    protected abstract int getIndex(String uuid);

}
