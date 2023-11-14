package com.javaops.webapp.storage;

import com.javaops.webapp.exception.ExistStorageException;
import com.javaops.webapp.exception.NotExistStorageException;
import com.javaops.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MapStorageTest {
    private final Storage storage = new MapStorage();
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4);
    private static final String UUID_NOT_EXIST = "notexist";

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    void saveIfExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
        assertSize(3);
    }

    @Test
    void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    void getIfNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST));
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    void deleteIfNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_NOT_EXIST));
        assertSize(3);
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
            Resume r = new Resume(UUID_NOT_EXIST);
            storage.update(r);
        });
    }

    @Test
    void size() {
        assertSize(3);
    }

    @Test
    void getAll() {
        Resume[] expected = new Resume[]{RESUME_2, RESUME_1, RESUME_3};
        Assertions.assertArrayEquals(expected, storage.getAll());
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        Resume[] allResume = storage.getAll();
        Assertions.assertArrayEquals(allResume, new Resume[0]);
    }

    void assertGet(Resume resume) {
        Assertions.assertEquals(storage.get(resume.getUuid()), resume);
    }

    void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }
}