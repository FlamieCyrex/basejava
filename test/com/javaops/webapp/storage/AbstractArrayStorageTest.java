package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.exception.StorageException;
import com.javaops.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

abstract class AbstractArrayStorageTest {


    private static Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        AbstractArrayStorageTest.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void save() {
        Resume r = new Resume("test");
        storage.save(r);
        Assertions.assertEquals(4, storage.size());
        Assertions.assertSame(storage.get("test"), r);
    }

    @Test
    void saveIfExist() {
        Resume r = new Resume(UUID_1);
        Assertions.assertThrows(ExistStorageException.class, () -> {
            storage.save(r);
        });
        Assertions.assertEquals(3, storage.size());

    }

    @Test
    void saveIfOverflowStorage() {
        while (storage.size()<10000){
            storage.save(new Resume());
        }
        Assertions.assertEquals(10000, storage.size());
        Assertions.assertThrows(StorageException.class, () -> {
            storage.save(new Resume());
        });
    }


    @Test
    void get() {
        Resume r = new Resume(UUID_1);
        Assertions.assertEquals(storage.get(UUID_1), r);
    }

    @Test
    void getIfNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get("test");
        });

    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        Assertions.assertEquals(2, storage.size());
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get(UUID_1);
        });
    }

    @Test
    void deleteIfNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete("test");
        });
        Assertions.assertEquals(3, storage.size());
    }


    @Test
    void update() {
        Resume r = new Resume(UUID_1);
        storage.update(r);
        Assertions.assertSame(storage.get(UUID_1), r);
    }

    @Test
    void updateIfNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            Resume r = new Resume("test");
            storage.update(r);
        });
    }

    @Test
    void size() {
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    void getAll() {
        Resume[] storage1 = storage.getAll();
        Assertions.assertEquals(storage1[0], new Resume(UUID_1));
        Assertions.assertEquals(storage1[1], new Resume(UUID_2));
        Assertions.assertEquals(storage1[2], new Resume(UUID_3));

    }

    @Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }
}